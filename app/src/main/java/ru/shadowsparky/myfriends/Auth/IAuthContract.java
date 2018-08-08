package ru.shadowsparky.myfriends.Auth;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface IAuthContract {
    interface IAuthView {
        void showToast(int message);
        void openActivity();
        Context getContext();
    }
    interface IAuthPresenter {
        Boolean checkAuth(int requestCode, int resultCode, @Nullable Intent data);
        void reAuth();
        void authError(int code);
    }
    interface IAuthModel {
    }
}
