package ru.shadowsparky.myfriends.MVP.Auth;

import android.app.Activity;

import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class AuthModel implements IAuthContract.AuthModel {
    private Activity activity;
    public AuthModel(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void auth() {
        VKSdk.login(activity, VKScope.FRIENDS);
    }

    @Override
    public void reAuth(VKCallback<VKSdk.LoginState> callback) {
        VKSdk.wakeUpSession(activity, callback);
    }
}
