package ru.shadowsparky.myfriends.Friends;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityOptionsCompat;
import ru.shadowsparky.myfriends.FriendsAdapter;
import ru.shadowsparky.myfriends.ICallbacks;
import ru.shadowsparky.myfriends.R;

public class FriendsListPresenter implements IFriends.IFriendsListPresenter {
    IFriends.IFriendsListView view;
    IFriends.IFriendsListModel model;
    ICallbacks.IGetFriends callback;
    ICallbacks.ITouchImage touchImageCallback;

    public FriendsListPresenter(IFriends.IFriendsListView view) {
        this.view = view;
        this.model = new FriendsListModel();
        callbackInit();
        touchImageCallbackInit();
    }

    @Override
    public void callbackInit() {
        callback = users -> {
            if (users != null) {
                if (users.size() != 0) {
                    view.friendsListIsEmpty(false);
                    FriendsAdapter adapter = new FriendsAdapter(users, touchImageCallback);
                    view.setAdapter(adapter);
                } else {
                    view.friendsListIsEmpty(true);
                }
            } else {
                view.showToast(R.string.connection_error);
            }
            Log.println(Log.DEBUG, "MAIN_TAG", Thread.currentThread().getName());
            view.setLoading(false);
        };
    }

    @Override
    public void getFriendsRequest() {
        view.setLoading(true);
        Thread thread = new Thread(()-> model.getFriends(callback));
        thread.start();
    }

    @Override
    public void touchImageCallbackInit() {
        touchImageCallback = (userData, image) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(view.getActivity(), image, view.getResourcesString(R.string.friends_image_transition));
                view.openImage(options.toBundle(), userData.photo_max);
            }
        };
    }
}
















































































































































































































































































































// люблю потоки