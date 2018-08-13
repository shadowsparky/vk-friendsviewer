package ru.shadowsparky.myfriends.MVP.OpenPhoto;

import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPhoto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.verify;

public class OpenPhotoPresenterTest {
    IOpenPhoto.OpenPhotoModel model;
    OpenPhotoPresenter presenter;
    IOpenPhoto.OpenPhotoView view;

    @Before
    public void setUp() throws Exception {
        model = mock(IOpenPhoto.OpenPhotoModel.class);
        view = mock(IOpenPhoto.OpenPhotoView.class);
        presenter = Mockito.spy(new OpenPhotoPresenter(view, model));
        presenter.cacher = mock(ImageCacher.class);
    }

    @Test
    public void onConnectionError() {
        presenter.onRequestHandled(null);
        verify(view).loadingError();
    }

    @Test
    public void onRequestHandledSuccessed() {
        VKApiModel user = mock(VKApiModel.class);
        presenter.onRequestHandled(user);
        presenter.onAdvancedMenuLoading(mock(VKApiPhoto.class));
    }

    @Test
    public void onAdvancedMenuLoading() {
        VKApiPhoto photo = mock(VKApiPhoto.class);
        presenter.onAdvancedMenuLoading(photo);
        verify(view).setMenuData(photo.likes, photo.tags, photo.comments);
    }

    @Test
    public void onGetPhotoRequest() {
        presenter.onGetPhotoRequest(10);
        verify(model).getPhoto(any(ICallbacks.IVKRequestCallback.class), eq(10));
    }

    @Test
    public void onImageDownloaded() {
        String url = "https://shadowsparky.ru";
        Bitmap image = mock(Bitmap.class);
        presenter.onImageDownloaded(image, url);
        verify(view).setImage(image);
        verify(presenter.cacher).saveImageToFile(url, image);
    }
}