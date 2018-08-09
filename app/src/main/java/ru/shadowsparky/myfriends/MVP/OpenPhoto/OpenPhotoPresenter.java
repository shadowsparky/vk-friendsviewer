package ru.shadowsparky.myfriends.MVP.OpenPhoto;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPhoto;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;

public class OpenPhotoPresenter implements IOpenPhoto.IOpenPhotoPresenter, ICallbacks.IVKRequestCallback {

    IOpenPhoto.IOpenPhotoView view;
    IOpenPhoto.IOpenPhotoModel model;
    ICallbacks.IDownloadImage getImageCallback;
    ImageCacher cacher = new ImageCacher();

    public OpenPhotoPresenter(IOpenPhoto.IOpenPhotoView view) {
        this.view = view;
        model = new OpenPhotoModel();
        initGetImageCallback();
    }

    @Override
    public void getPhotoRequest(int ID) {
        model.getPhoto(this::handleRequest, ID);
    }

    @Override
    public void initGetImageCallback() {
        getImageCallback = (image, name) -> {
            view.setImage(image);
            cacher.saveImageToFile(name, image);
        };
    }

    @Override
    public void advancedInfoShower(VKApiPhoto result) {
        view.setMenuData(result.likes, result.tags, result.comments);
    }

    @Override
    public String hdPhotoChecker(VKApiPhoto photo) {
        String url = photo.photo_1280;
        if (url.equals("")) {
            url = photo.photo_604;
        }
        return url;
    }

    @Override
    public void handleRequest(VKApiModel photo) {
        if (photo instanceof VKApiPhoto) {
            cacher.cachedPhotoChecker(hdPhotoChecker((VKApiPhoto) photo), getImageCallback);
            advancedInfoShower((VKApiPhoto) photo);
        } else {
            view.loadingError();
        }
    }
}
