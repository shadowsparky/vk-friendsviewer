package ru.shadowsparky.myfriends.Auth;

import android.content.Intent;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import androidx.annotation.Nullable;
import ru.shadowsparky.myfriends.R;

public class AuthPresenter implements IAuthContract.IAuthPresenter {
    IAuthContract.IAuthView view;

    public AuthPresenter(IAuthContract.IAuthView view) {
        this.view = view;
    }

    @Override
    public Boolean checkAuth(int requestCode, int resultCode, @Nullable Intent data) {
        return VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                view.showToast(R.string.auth_ok);
                view.openActivity();
            }

            @Override
            public void onError(VKError error) {
                authError(error.errorCode);
            }
        });
    }

    @Override
    public void reAuth() {
        VKSdk.wakeUpSession(view.getContext(), new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch(res){
                    case LoggedIn:
                        view.openActivity();
                        break;
                }
            }

            @Override
            public void onError(VKError error) {
                authError(error.errorCode);
            }
        });
    }

    @Override
    public void authError(int code) {
        if (code == -102) {
            view.showToast(R.string.auth_cancel);
        } else if (code != -102) {
            view.showToast(R.string.auth_external_error);
        } else {
            throw new RuntimeException("???");
        }
    }

}
