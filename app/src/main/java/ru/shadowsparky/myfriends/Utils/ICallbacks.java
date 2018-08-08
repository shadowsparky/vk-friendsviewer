package ru.shadowsparky.myfriends.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKUsersArray;

public interface ICallbacks {
    interface IGetFriends {
        void getFriendsCallback(VKUsersArray users);
    }
    interface IDownloadImage {
        void downloadImageCallback(Bitmap image, String url);
    }
    interface ITouchImage {
        void touchImageCallback(VKApiUserFull userData, ImageView image);
    }
    interface IScrollEnd {
        void scrollEndCallback(int offset);
    }
    interface IFullImage {
        void getFullImageCallback(VKApiPhoto photo);
    }
}
