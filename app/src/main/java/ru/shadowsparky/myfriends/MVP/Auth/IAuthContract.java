package ru.shadowsparky.myfriends.MVP.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface IAuthContract {
    interface IAuthView {
        void showToast(int message);
        void openActivity();
        Context getContext();
        Activity getActivity();
    }
    interface IAuthPresenter {
        Boolean authCallback(int requestCode, int resultCode, @Nullable Intent data);
        void reAuth();
        void authError(int code);
        void sendAuthRequest(Activity activity);
    }
    interface IAuthModel {
        void auth(Activity activity);
    }
}
