package ru.shadowsparky.myfriends.MVP.Friends;

import com.vk.sdk.api.VKParameters;

import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.VKUniversalRequest;

import static ru.shadowsparky.myfriends.Utils.Consts.FRIENDS_GET_METHOD;

public class FriendsListModel extends VKUniversalRequest implements IFriends.IFriendsListModel {
    @Override
    public void getFriends(ICallbacks.IVKRequestCallback callback, int offset) {
        VKParameters params = new VKParameters();
        params.put("order", "hints");
        params.put("offset", offset);
        params.put("count", 20);
        params.put("fields", "photo_200");
        super.executeRequest(FRIENDS_GET_METHOD, params, callback);
    }
}
