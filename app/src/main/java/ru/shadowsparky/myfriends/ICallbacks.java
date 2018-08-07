package ru.shadowsparky.myfriends;

import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKUsersArray;

public interface ICallbacks {
    interface IGetFriends {
        void getFriendsCallback(VKUsersArray users);
    }
    interface IDownloadImage {
        void downloadImageCallback(Bitmap image);
    }
}
