package ru.shadowsparky.myfriends.Utils;

import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKUsersArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUniversalRequest {
    public void executeRequest(String method, VKParameters params, ICallbacks.IVKRequestCallback callback) {
        VKRequest request = new VKRequest(method, params);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                callback.handleRequest(parseResult(method, response.json));
                Log.println(Log.DEBUG, "MAIN_TAG", "Request handled: " + method);
            }

            @Override
            public void onError(VKError error) {
                callback.handleRequest(null);
                Log.println(Log.DEBUG, "MAIN_TAG", "Error on request: " + method);
            }
        });
    }

    public VKApiModel parseResult(String method, JSONObject response) {
        VKApiModel result = null;
        try {
            switch (method) {
                case "friends.get":
                    result = getFriendsParser(new VKUsersArray(), response);
                    break;
                case "photos.get":
                    result = getPhotoParser(new VKPhotoArray(), response);
                    break;
            }
        } catch (JSONException e) {
            Log.println(Log.DEBUG, "MAIN_TAG", "An error occurred while parsing: " + e.toString());
        }
        return result;
    }

    public VKApiModel getFriendsParser(VKUsersArray users, JSONObject response) throws JSONException {
        users.parse(response);
        return users;
    }

    public VKApiModel getPhotoParser(VKPhotoArray photos, JSONObject response) throws JSONException {
        photos.parse(response);
        if (photos.size() > 0) {
            return photos.get(0);
        }
        return null;
    }

}