package ru.shadowsparky.myfriends.Friends;

import android.util.Log;

import ru.shadowsparky.myfriends.FriendsAdapter;
import ru.shadowsparky.myfriends.ICallbacks;

public class FriendsListPresenter implements IFriends.IFriendsListPresenter {
    IFriends.IFriendsListView view;
    IFriends.IFriendsListModel model;
    ICallbacks.IGetFriends callback;

    public FriendsListPresenter(IFriends.IFriendsListView view) {
        this.view = view;
        this.model = new FriendsListModel();
        callbackInit();
    }

    @Override
    public void callbackInit() {
        callback = users -> {
            if (users != null) {
                FriendsAdapter adapter = new FriendsAdapter(users);
                view.setAdapter(adapter);
            } else {
                Log.println(Log.DEBUG, "MAIN_TAG", "Internal Error");
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