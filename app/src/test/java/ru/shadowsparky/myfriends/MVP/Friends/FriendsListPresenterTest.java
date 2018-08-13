package ru.shadowsparky.myfriends.MVP.Friends;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.R;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FriendsListPresenterTest {
    IFriends.FriendsListModel model;
    FriendsListPresenter presenter;
    IFriends.FriendsListView view;
    VKApiModel vkRequestResult;
    VKApiUserFull userData;
    @Before
    public void setUp() throws Exception {
        model = mock(IFriends.FriendsListModel.class);
        view = mock(IFriends.FriendsListView.class);
        presenter = Mockito.spy(new FriendsListPresenter(view, model));
//        presenter = new FriendsListPresenter(view, model);
        presenter.adapter = mock(FriendsAdapter.class);
        vkRequestResult = mock(VKApiModel.class);
        // fixme поскольку разработчики VKSDK используют зависимости андроида в своей библиотеке, то
        // некоторые объекты, такие как VKApiUserFull нельзя протестировать.
        // поэтому стандартное значение null, но давайте представим, будто бы тут находится нормальный
        // объект
        userData = null;
    }
    // Change Build.Version
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    @Test
    public void onConnectionError() {
        presenter.onRequestHandled(null);
        verify(view).showToast(R.string.connection_error);
    }

    @Test
    public void onImageTouchedEqualsOrGreatherThanLollipop() throws Exception {
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 27);
        ImageView image = new ImageView(mock(Context.class));
        presenter.onImageTouched(userData, image);
        verify(view).openImage(userData, image);
    }

    @Test
    public void onImageTouchedUnderLollipop() throws Exception {
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        presenter.onImageTouched(userData, null);
        verify(view).openImage(userData, null);
    }

    @Test
    public void onFriendsNotFound() {
        presenter.onRequestHandled(new VKUsersArray());
        verify(view).friendsListIsEmpty(true);
    }

    @Test
    public void onFriendsFounded() {
        VKUsersArray users = mock(VKUsersArray.class);
        Mockito.when(users.size()).thenReturn(100);
        presenter.onRequestHandled(users);
        verify(presenter).onFriendsExists(users);
    }

    @Test
    public void onFriendsExistsAndAdapterIsNull() {
        presenter.adapter = null;
        VKUsersArray data = new VKUsersArray();
        presenter.onFriendsExists(data);
        verify(view).friendsListIsEmpty(false);
        verify(view).setAdapter(presenter.adapter);
        verify(view).setLoading(false);
    }

    @Test
    public void onFriendsExistsAndAdapterNotNull() {
        presenter.adapter = mock(FriendsAdapter.class);
        VKUsersArray data = new VKUsersArray();
        presenter.onFriendsExists(data);
        verify(presenter.adapter).addData(data);
        verify(view).setLoading(false);
    }

    @Test
    public void onScroll() {
        Mockito.when(presenter.adapter.maxFriendsCount()).thenReturn(60);
        presenter.onScrollEnded(20);
        verify(presenter).onGetFriendsRequest(20);
    }

    @Test
    public void onScrollEnded() {
        Mockito.when(presenter.adapter.maxFriendsCount()).thenReturn(100);
        presenter.onScrollEnded(100);
        verify(presenter, times(0)).onGetFriendsRequest(100);
    }

    @Test
    // FIXME different arguments (почему?)
    public void onGetFriendsRequest() {
        int offset = 0;
        presenter.onGetFriendsRequest(offset);
        verify(view).setLoading(true);
        /* почему presenter::onRequestHandled != onRequestHandled
         * внутри presenter.onGetFriendsRequest? */
        verify(model).getFriends(presenter::onRequestHandled, offset);
    }
}