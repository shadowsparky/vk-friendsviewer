package ru.shadowsparky.myfriends.OpenPhoto;

import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKPhotoArray;

import org.json.JSONException;

import ru.shadowsparky.myfriends.Utils.ICallbacks;

public class OpenPhotoModel implements IOpenPhoto.IOpenPhotoModel {
    @Override
    public void getPhoto(ICallbacks.IFullImage callback, int UserID) {
        VKParameters parameters = new VKParameters();
        parameters.put("owner_id", UserID);
        parameters.put("album_id", "profile");
        parameters.put("rev", 1);
        parameters.put("extended", 1);
        parameters.put("count", 1);
        VKRequest request = new VKRequest("photos.get", parameters);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKPhotoArray f = new VKPhotoArray();
                try {
                    f.parse(response.json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (f.size() > 0) {
                    callback.getFullImageCallback(f.get(0));
                } else {
                    callback.getFullImageCallback(null);
                }
//                f.get(0);
//                Log.println(Log.DEBUG, "MAIN_TAG", response.responseString);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                callback.getFullImageCallback(null);
            }
        });
    }
}
