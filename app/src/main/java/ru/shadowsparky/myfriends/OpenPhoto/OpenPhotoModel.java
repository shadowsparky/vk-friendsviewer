package ru.shadowsparky.myfriends.OpenPhoto;

import android.util.Log;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKPhotoArray;

import org.json.JSONException;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.VKUniversalRequest;

public class OpenPhotoModel extends VKUniversalRequest implements IOpenPhoto.IOpenPhotoModel {

    public static final String GET_PHOTOS_METHOD = "photos.get";

    @Override
    public void getPhoto(ICallbacks.IVKRequestCallback callback, int UserID) {
        VKParameters parameters = new VKParameters();
        parameters.put("owner_id", UserID);
        parameters.put("album_id", "profile");
        parameters.put("rev", 1);
        parameters.put("extended", 1);
        parameters.put("count", 1);
        super.executeRequest(GET_PHOTOS_METHOD, parameters, callback);
    }
}
