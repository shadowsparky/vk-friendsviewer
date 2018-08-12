package ru.shadowsparky.myfriends.MVP.Friends;

import android.content.Context;
import android.os.Bundle;

import com.vk.sdk.api.model.VKApiUserFull;

import org.junit.Before;
import org.junit.Test;

import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.Utils.ICallbacks;

import static org.junit.Assert.*;

public class FriendsListPresenterTest {


    IFriends.IFriendsListView view;
    IFriends.IFriendsListModel model = new FriendsListModel();

    @Before
    public void setUp() throws Exception {
        view = new IFriends.IFriendsListView() {
            @Override
            public void setLoading(boolean result) {

            }

            @Override
            public void setAdapter(FriendsAdapter adapter) {

            }

            @Override
            public void friendsListIsEmpty(boolean result) {

            }

            @Override
            public void showToast(int message) {

            }

            @Override
            public String getResourcesString(int id) {
                return null;
            }

            @Override
            public void openImage(Bundle bundle, VKApiUserFull user) {

            }

            @Override
            public AppCompatActivity getActivity() {
                return null;
            }

            @Override
            public Context getContext() {
                return null;
            }
        };
    }

    @Test
    public void getFriendsRequest() {
        ICallbacks.IVKRequestCallback callback = (s) -> {};
        model.getFriends(callback, 0);
    }
}