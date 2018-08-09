package ru.shadowsparky.myfriends.Friends;

import android.content.Context;
import android.os.Bundle;

import com.vk.sdk.api.model.VKApiUserFull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.runner.AndroidJUnit4;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.Utils.ICallbacks;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FriendsListPresenterTest {
    IFriends.IFriendsListView view;
    IFriends.IFriendsListModel model;
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
        model = new FriendsListModel();
    }

    @Test
    public void getFriendsRequest() {
        int offset = 0;
        ICallbacks.IGetFriends callback = users -> assertEquals(users.getCount(), 29);
        model.getFriends(callback, offset);
    //        view.setLoading(true);
    //        if ((offset == 0) && (adapter != null)) {
    //            adapter.removeAllData();
    //        }
    //        Thread thread = new Thread(()-> model.getFriends(callback, offset));
    //        thread.start();
    }
}