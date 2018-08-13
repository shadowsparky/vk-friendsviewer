package ru.shadowsparky.myfriends.Adapter;

import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FriendsAdapterTest {
    VKUsersArray users = mock(VKUsersArray.class);
    ICallbacks.ITouchImage touchImageCallback = mock(ICallbacks.ITouchImage.class);
    FriendsAdapter adapter;
    ICallbacks.IScrollEnd scrollingEndCallback = mock(ICallbacks.IScrollEnd.class);
    ImageCacher imgCacher = new ImageCacher();

    @Before
    public void setUp() {
        Mockito.when(users.size()).thenReturn(0);
        Mockito.when(users.getCount()).thenReturn(100);
        adapter = new FriendsAdapter(users, touchImageCallback, scrollingEndCallback);
    }

    @Test
    public void test() {

    }
}