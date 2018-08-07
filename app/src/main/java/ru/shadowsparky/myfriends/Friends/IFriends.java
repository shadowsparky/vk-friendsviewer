package ru.shadowsparky.myfriends.Friends;

import com.vk.sdk.api.model.VKUsersArray;

import ru.shadowsparky.myfriends.FriendsAdapter;
import ru.shadowsparky.myfriends.ICallbacks;

public interface IFriends {
    interface IFriendsListView {
        void setLoading(boolean result);
        void setAdapter(FriendsAdapter adapter);
    }

    interface IFriendsListPresenter {
        void callbackInit();
        void getFriendsRequest();
    }

    interface IFriendsListModel {
        void getFriends(ICallbacks.IGetFriends callback);
    }
}
