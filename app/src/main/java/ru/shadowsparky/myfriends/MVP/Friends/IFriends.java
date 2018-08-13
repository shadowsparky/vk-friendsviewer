package ru.shadowsparky.myfriends.MVP.Friends;

import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.Utils.ICallbacks;

public interface IFriends {
    interface FriendsListView {
        void setLoading(boolean result);
        void setAdapter(FriendsAdapter adapter);
        void friendsListIsEmpty(boolean result);
        void showToast(int message_id);
        void showNecessaryPermissionDialog();
        void openImage(VKApiUserFull user, ImageView image);
    }

    interface FriendsListPresenter extends ICallbacks.IVKRequestCallback, ICallbacks.IScrollEnd, ICallbacks.ITouchImage {
        void onScrollEnded(int offset);
        void onImageTouched(VKApiUserFull userData, ImageView image);
        void onGetFriendsRequest(int offset);
        void onFriendsExists(VKUsersArray users);
        void onRequestHandled(VKApiModel photo);
    }

    interface FriendsListModel {
        void getFriends(ICallbacks.IVKRequestCallback callback, int offset);
    }
}
