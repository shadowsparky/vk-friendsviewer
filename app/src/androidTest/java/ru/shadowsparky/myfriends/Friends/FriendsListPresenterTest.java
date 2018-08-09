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

    }

    @Test
    public void getFriendsRequest() {
    }
}