package ru.shadowsparky.myfriends.MVP.Auth;

import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;

public interface IAuthContract {
    interface AuthView {
        void showToast(int message_id);
        void openActivity();
    }
    interface AuthPresenter {
        void onAuthSuccess();
        void onAuthError(int code);
        void onReAuthRequest();
        void onAuthRequest();
        VKCallback<VKSdk.LoginState> onReAuthResultHandler();
    }
    interface AuthModel {
        void auth();
        void reAuth(VKCallback<VKSdk.LoginState> callback);
    }
}
