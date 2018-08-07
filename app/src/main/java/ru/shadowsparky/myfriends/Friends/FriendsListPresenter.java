package ru.shadowsparky.myfriends.Friends;

import android.os.Build;

import com.vk.sdk.api.model.VKUsersArray;

import androidx.core.app.ActivityOptionsCompat;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.R;

public class FriendsListPresenter implements IFriends.IFriendsListPresenter {
    IFriends.IFriendsListView view;
    IFriends.IFriendsListModel model;
    ICallbacks.IGetFriends callback;
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
        callback = users -> adapterWorker(users);
        endCallback = (offset -> {
            if (offset == 0) {
                adapter = null;
            }
            if (offset != adapter.maxFriendsCount()) {
                getFriendsRequest(offset);
            }
        });

    }

    @Override
    public void getFriendsRequest(int offset) {
        view.setLoading(true);
        Thread thread = new Thread(()-> model.getFriends(callback, offset));
        thread.start();
    }

    @Override
    public void touchImageCallbackInit() {
        touchImageCallback = (userData, image) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(view.getActivity(), image, view.getResourcesString(R.string.friends_image_transition));
                view.openImage(options.toBundle(), userData.photo_max);
            } else {
                view.openImage(null, userData.photo_max);
            }
        };
    }

    @Override
    public void adapterWorker(VKUsersArray users) {
        checkNullUsers(users);
        view.setLoading(false);
    }

    @Override
    public void checkNullUsers(VKUsersArray users) {
        if (users != null) {
           checkFriendsNotFound(users);
        } else {
            view.showToast(R.string.connection_error);
        }
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
    public void checkAdapter(VKUsersArray users) {
        if (adapter == null) {
            view.friendsListIsEmpty(false);
            adapter = new FriendsAdapter(users, touchImageCallback, endCallback);
            view.setAdapter(adapter);
        } else {
            adapter.addData(users);
        }
    }
}
















































































































































































































































































































// люблю потоки