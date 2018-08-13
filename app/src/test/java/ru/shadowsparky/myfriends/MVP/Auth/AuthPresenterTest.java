package ru.shadowsparky.myfriends.MVP.Auth;

import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ru.shadowsparky.myfriends.R;
import ru.shadowsparky.myfriends.Utils.ICallbacks;

import static com.vk.sdk.api.VKError.VK_CANCELED;
import static com.vk.sdk.api.VKError.VK_REQUEST_HTTP_FAILED;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthPresenterTest {
    IAuthContract.AuthPresenter presenter;
    IAuthContract.AuthModel model;
    IAuthContract.AuthView view;
    @Before
    public void setUp() throws Exception {
        view = mock(IAuthContract.AuthView.class);
        model = mock(IAuthContract.AuthModel.class);
        presenter = new AuthPresenter(view, model);
    }

    @Test
    public void onAuthSuccessed() {
        presenter.onAuthSuccess();
        verify(view).showToast(R.string.auth_ok);
        verify(view).openActivity();
    }

    @Test
    public void onAuthFailedBecauseCanceled() {
        presenter.onAuthError(VK_CANCELED);
        verify(view).showToast(R.string.auth_cancel);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onAuthFailedBecauseConnectionError() {
        presenter.onAuthError(VK_REQUEST_HTTP_FAILED);
        verify(view).showToast(R.string.connection_error);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onAuthFailedBecauseExternalError() {
        presenter.onAuthError(100500);
        verify(view).showToast(R.string.auth_external_error);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onAuthRequest() {
        presenter.onAuthRequest();
        verify(model).auth();
    }

    @Test
    public void onReAuthRequest() {
        presenter.onReAuthRequest();
        verify(model).reAuth(any(VKCallback.class));
    }
}