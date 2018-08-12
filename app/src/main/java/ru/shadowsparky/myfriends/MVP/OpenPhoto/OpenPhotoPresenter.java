package ru.shadowsparky.myfriends.MVP.OpenPhoto;

import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPhoto;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;

public class OpenPhotoPresenter implements IOpenPhoto.OpenPhotoPresenter {
    IOpenPhoto.OpenPhotoView view;
    IOpenPhoto.OpenPhotoModel model;
    ImageCacher cacher = new ImageCacher();

    public OpenPhotoPresenter(IOpenPhoto.OpenPhotoView view, IOpenPhoto.OpenPhotoModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onImageDownloaded(Bitmap image, String url) {
        view.setImage(image);
        cacher.saveImageToFile(url, image);
    }

    @Override
    public void onGetPhotoRequest(int ID) {
        model.getPhoto(this::onRequestHandled, ID);
    }

    @Override
    public void onAdvancedMenuLoading(VKApiPhoto photo) {
        view.setMenuData(photo.likes, photo.tags, photo.comments);
    }

    @Override
    public void onRequestHandled(VKApiModel photo) {
        if (photo instanceof VKApiPhoto) {
            cacher.cachedPhotoChecker(cacher.hdPhotoChecker((VKApiPhoto) photo), this::onImageDownloaded);
            onAdvancedMenuLoading((VKApiPhoto) photo);
        } else {
            view.loadingError();
        }
    }
}
