package ru.shadowsparky.myfriends.Friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vk.sdk.api.model.VKUsersArray;

import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.FriendsAdapter;
import ru.shadowsparky.myfriends.ICallbacks;

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
        void getFriendsRequest();
        void touchImageCallbackInit();
    }

    interface IFriendsListModel {
        void getFriends(ICallbacks.IGetFriends callback);
    }
}
