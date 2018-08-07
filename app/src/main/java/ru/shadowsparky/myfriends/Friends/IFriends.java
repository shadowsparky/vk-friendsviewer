package ru.shadowsparky.myfriends.Friends;

import android.os.Bundle;

import com.vk.sdk.api.model.VKUsersArray;

import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.Utils.ICallbacks;

public interface IFriends {
    interface IFriendsListView {
        void setLoading(boolean result);
        void setAdapter(FriendsAdapter adapter);
        void friendsListIsEmpty(boolean result);
        void showToast(int message);
        String getResourcesString(int id);
        void openImage(Bundle bundle, String url);
        AppCompatActivity getActivity();
    }

    interface IFriendsListPresenter {
        void callbackInit();
        void getFriendsRequest(int offset);
        void touchImageCallbackInit();
        void adapterWorker(VKUsersArray users);
        void checkNullUsers(VKUsersArray users);
        void checkFriendsNotFound(VKUsersArray users);
        void checkAdapter(VKUsersArray users);
    }

    interface IFriendsListModel {
        void getFriends(ICallbacks.IGetFriends callback, int Offset);
    }
}
