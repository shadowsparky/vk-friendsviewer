package ru.shadowsparky.myfriends.OpenPhoto;

import android.graphics.Bitmap;

import ru.shadowsparky.myfriends.Utils.ICallbacks;

public interface IOpenPhoto {
    interface IOpenPhotoView {
        void setImage(Bitmap image);
        void loadingError();
        void setMenuData(int likeCount, int tagCount, int commentsCount);
    }
    interface IOpenPhotoPresenter {
        void getPhotoRequest(int ID);
        void initRequestResultCallback();
        void initGetImageCallback();
    }
    interface IOpenPhotoModel {
        void getPhoto(ICallbacks.IFullImage callback, int UserID);
    }
}