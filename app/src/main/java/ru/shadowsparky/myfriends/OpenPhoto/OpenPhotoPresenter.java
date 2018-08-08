package ru.shadowsparky.myfriends.OpenPhoto;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageDownloader;

public class OpenPhotoPresenter implements IOpenPhoto.IOpenPhotoPresenter {
    ICallbacks.IFullImage callback;
    IOpenPhoto.IOpenPhotoView view;
    IOpenPhoto.IOpenPhotoModel model;
    ICallbacks.IDownloadImage downloadCallback;

    public OpenPhotoPresenter(IOpenPhoto.IOpenPhotoView view) {
        this.view = view;
        model = new OpenPhotoModel();
        downloadCallback = (image) -> view.setImage(image);
        callback = (requestResult) -> {
            ImageDownloader downloader = new ImageDownloader(downloadCallback);
            if (requestResult != null) {
                downloader.execute(requestResult.photo_1280);
            } else {
                view.loadingError();
            }
        };
    }

    @Override
    public void getPhotoRequest(int ID) {
        model.getPhoto(callback, ID);
    }
}
