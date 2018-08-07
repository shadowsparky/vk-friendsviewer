package ru.shadowsparky.myfriends;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

public interface ICallbacks {
    interface IGetFriends {
        void getFriendsCallback(VKUsersArray users);
    }
    interface IDownloadImage {
        void downloadImageCallback(Bitmap image);
    }
    interface ITouchImage {
        void touchImageCallback(VKApiUserFull userData, ImageView image);
    }
}
