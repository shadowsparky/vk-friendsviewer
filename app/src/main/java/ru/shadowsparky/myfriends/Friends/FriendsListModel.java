package ru.shadowsparky.myfriends.Friends;

import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKUsersArray;

import ru.shadowsparky.myfriends.ICallbacks;

public class FriendsListModel implements IFriends.IFriendsListModel {

    @Override
    public void getFriends(ICallbacks.IGetFriends callback) {
        VKParameters params = new VKParameters();
        params.put("order", "hints");
        params.put("count", 20);
        params.put("fields", "photo_200, photo_max");
        VKRequest request = VKApi.friends().get(params);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                callback.getFriendsCallback((VKUsersArray) response.parsedModel);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                callback.getFriendsCallback(null);
                Log.println(Log.DEBUG, "MAIN_TAG", error.errorCode + " " + error.toString());
            }
        });
    }
}
