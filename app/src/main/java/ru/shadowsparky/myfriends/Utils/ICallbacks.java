package ru.shadowsparky.myfriends.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKUsersArray;

public interface ICallbacks {
    interface IDownloadImage {
        void onImageDownloaded(Bitmap image, String url);
    }
    interface ITouchImage {
        void onImageTouched(VKApiUserFull userData, ImageView image);
    }
    interface IScrollEnd {
        void onScrollEnded(int offset);
    }
    interface IVKRequestCallback {
        void onRequestHandled(VKApiModel result);
    }
}
