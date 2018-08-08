package ru.shadowsparky.myfriends.OpenPhoto;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;

public class OpenPhotoPresenter implements IOpenPhoto.IOpenPhotoPresenter {
    ICallbacks.IFullImage callback;
    IOpenPhoto.IOpenPhotoView view;
    IOpenPhoto.IOpenPhotoModel model;
    ICallbacks.IDownloadImage getImageCallback;
    ImageCacher cacher = new ImageCacher();

    public OpenPhotoPresenter(IOpenPhoto.IOpenPhotoView view) {
        this.view = view;
        model = new OpenPhotoModel();
        initGetImageCallback();
        initRequestResultCallback();
    }

    @Override
    public void getPhotoRequest(int ID) {
        model.getPhoto(callback, ID);
    }

    @Override
    public void initRequestResultCallback() {
        callback = (requestResult) -> {
            if (requestResult != null) {
                cacher.cachedPhotoChecker(requestResult.photo_1280, getImageCallback);
            } else {
                view.loadingError();
            }
        };
    }

    @Override
    public void initGetImageCallback() {
        getImageCallback = (image, name) -> {
            view.setImage(image);
            cacher.saveImageToFile(name, image);
        };
    }
}
