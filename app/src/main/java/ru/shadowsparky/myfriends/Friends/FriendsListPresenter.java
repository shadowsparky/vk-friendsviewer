package ru.shadowsparky.myfriends.Friends;

import android.util.Log;

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
    }

    @Override
    public void callbackInit() {
        touchImageCallback = userData -> {
            Log.println(Log.DEBUG, "MAIN_TAG", userData.first_name + " " + userData.id);
        };
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
        // fixme поток, чтобы солиднее выглядело. на самом деле он не нужен...
        Thread thread = new Thread(()-> model.getFriends(callback));
        thread.start();
    }
}
















































































































































































































































































































// люблю потоки