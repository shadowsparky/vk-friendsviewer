package ru.shadowsparky.myfriends.Auth;

import android.app.Activity;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

public class AuthModel implements IAuthContract.IAuthModel {

    @Override
    public void auth(Activity activity) {
        VKSdk.login(activity, VKScope.FRIENDS);
    }
}
