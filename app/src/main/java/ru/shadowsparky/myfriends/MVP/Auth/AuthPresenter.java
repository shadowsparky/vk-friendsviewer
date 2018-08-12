package ru.shadowsparky.myfriends.MVP.Auth;

import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.shadowsparky.myfriends.R;

import static com.vk.sdk.api.VKError.VK_CANCELED;
import static com.vk.sdk.api.VKError.VK_REQUEST_HTTP_FAILED;

public class AuthPresenter implements IAuthContract.AuthPresenter {
    private IAuthContract.AuthView view;
    private IAuthContract.AuthModel model;

    public AuthPresenter(IAuthContract.AuthView view, IAuthContract.AuthModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAuthSuccess() {
        view.showToast(R.string.auth_ok);
        view.openActivity();
    }

    @Override
    public void onReAuthRequest() {
        model.reAuth(onReAuthResultHandler());
    }

    @Override
    public VKCallback<VKSdk.LoginState> onReAuthResultHandler() {
        return new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch(res){
                    case LoggedIn:
                        onAuthSuccess();
                        break;
                }
            }

            @Override
            public void onError(VKError error) {
                onAuthError(error.errorCode);
            }
        };
    }

    @Override
    public void onAuthError(int code) {
        if (code == VK_CANCELED) {
            view.showToast(R.string.auth_cancel);
        } else if (code == VK_REQUEST_HTTP_FAILED) {
            view.showToast(R.string.connection_error);
        }
    }

    @Override
    public void onAuthRequest() {
        model.auth();
    }
}
