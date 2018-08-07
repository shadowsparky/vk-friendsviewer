package ru.shadowsparky.myfriends.Utils;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class VKInitialize extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
