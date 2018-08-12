package ru.shadowsparky.myfriends.MVP.OpenPhoto;

import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPhoto;

import ru.shadowsparky.myfriends.Utils.ICallbacks;

public interface IOpenPhoto {
    interface OpenPhotoView {
        void setImage(Bitmap image);
        void loadingError();
        void setMenuData(int likeCount, int tagCount, int commentsCount);
    }
    interface OpenPhotoPresenter extends ICallbacks.IVKRequestCallback, ICallbacks.IDownloadImage {
        void onImageDownloaded(Bitmap image, String url);
        void onGetPhotoRequest(int ID);
        void onAdvancedMenuLoading(VKApiPhoto photo);
        void onRequestHandled(VKApiModel result);
    }
    interface OpenPhotoModel {
        void getPhoto(ICallbacks.IVKRequestCallback callback, int UserID);
    }
}