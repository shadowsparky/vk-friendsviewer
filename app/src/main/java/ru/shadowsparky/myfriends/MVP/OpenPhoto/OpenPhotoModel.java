package ru.shadowsparky.myfriends.MVP.OpenPhoto;

import com.vk.sdk.api.VKParameters;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.VKUniversalRequest;

import static ru.shadowsparky.myfriends.Utils.Consts.PHOTOS_GET_METHOD;

public class OpenPhotoModel extends VKUniversalRequest implements IOpenPhoto.IOpenPhotoModel {

    @Override
    public void getPhoto(ICallbacks.IVKRequestCallback callback, int UserID) {
        VKParameters parameters = new VKParameters();
        parameters.put("owner_id", UserID);
        parameters.put("album_id", "profile");
        parameters.put("rev", 1);
        parameters.put("extended", 1);
        parameters.put("count", 1);
        super.executeRequest(PHOTOS_GET_METHOD, parameters, callback);
    }
}
