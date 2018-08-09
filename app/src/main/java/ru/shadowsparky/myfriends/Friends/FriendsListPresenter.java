package ru.shadowsparky.myfriends.Friends;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKUsersArray;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.R;

public class FriendsListPresenter implements IFriends.IFriendsListPresenter, ICallbacks.IVKRequestCallback {
    IFriends.IFriendsListView view;
    IFriends.IFriendsListModel model;
    ICallbacks.ITouchImage touchImageCallback;
    ICallbacks.IScrollEnd endCallback;
    FriendsAdapter adapter;

    public FriendsListPresenter(IFriends.IFriendsListView view) {
        this.view = view;
        this.model = new FriendsListModel();
        callbackInit();
        touchImageCallbackInit();
    }

    @Override
    public void callbackInit() {
        endCallback = (offset -> {
            if (offset != adapter.maxFriendsCount()) {
                getFriendsRequest(offset);
            }
        });
    }

    @Override
    public void getFriendsRequest(int offset) {
        view.setLoading(true);
        if ((offset == 0) && (adapter != null)) {
            adapter.removeAllData();
        }
        Thread thread = new Thread(()-> model.getFriends(this::handleRequest, offset));
        thread.start();
    }

    @Override
    public void touchImageCallbackInit() {
        touchImageCallback = (userData, image) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(view.getActivity(), image, view.getResourcesString(R.string.friends_image_transition));
                view.openImage(options.toBundle(), userData);
            } else {
                view.openImage(null, userData);
            }
        };
    }

    @Override
    public void checkFriendsNotFound(VKUsersArray users) {
        if (users.size() != 0) {
            checkAdapter(users);
        } else {
            view.friendsListIsEmpty(true);
        }
    }

    @Override
    public void storageChecker() {
        if (ContextCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            Log.println(Log.DEBUG, "MAIN_TAG", "Permission dont granted");
        } else {
            Log.println(Log.DEBUG, "MAIN_TAG", "Permission granted");
        }
    }

    @Override
    public void checkAdapter(VKUsersArray users) {
        if (adapter == null) {
            view.friendsListIsEmpty(false);
            adapter = new FriendsAdapter(users, touchImageCallback, endCallback);
            view.setAdapter(adapter);
        } else {
            adapter.addData(users);
        }
        view.setLoading(false);
    }

    @Override
    public void handleRequest(VKApiModel result) {
        if (result instanceof VKUsersArray) {
            checkFriendsNotFound((VKUsersArray)result);
        } else {
            view.showToast(R.string.connection_error);
            view.setLoading(false);
        }
    }
}
















































































































































































































































































































// люблю потоки