package ru.shadowsparky.myfriends.Friends;

import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKUsersArray;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.VKUniversalRequest;

public class FriendsListModel extends VKUniversalRequest implements IFriends.IFriendsListModel {
    @Override
    public void getFriends(ICallbacks.IVKRequestCallback callback, int offset) {
        VKParameters params = new VKParameters();
        params.put("order", "hints");
        params.put("offset", offset);
        params.put("count", 20);
        params.put("fields", "photo_200");
        super.executeRequest("friends.get", params, callback);
    }
}
