package ru.shadowsparky.myfriends.OpenPhoto;

import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKApiPhoto;

import ru.shadowsparky.myfriends.Utils.ICallbacks;

public interface IOpenPhoto {
    interface IOpenPhotoView {
        void setImage(Bitmap image);
        void loadingError();
        void setMenuData(int likeCount, int tagCount, int commentsCount);
    }
    interface IOpenPhotoPresenter {
        void getPhotoRequest(int ID);
        void initGetImageCallback();
        void advancedInfoShower(VKApiPhoto photo);
        String hdPhotoChecker(VKApiPhoto photo);
    }
    interface IOpenPhotoModel {
        void getPhoto(ICallbacks.IVKRequestCallback callback, int UserID);
    }
}