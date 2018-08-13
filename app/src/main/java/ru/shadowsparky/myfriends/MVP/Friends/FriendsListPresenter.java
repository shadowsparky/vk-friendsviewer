package ru.shadowsparky.myfriends.MVP.Friends;

import android.os.Build;
import android.widget.ImageView;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.R;

public class FriendsListPresenter implements IFriends.FriendsListPresenter {
    IFriends.FriendsListView view;
    IFriends.FriendsListModel model;
    FriendsAdapter adapter;

    public FriendsListPresenter(IFriends.FriendsListView view, IFriends.FriendsListModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onScrollEnded(int offset) {
        if (offset != adapter.maxFriendsCount()) {
            onGetFriendsRequest(offset);
        }
    }

    @Override
    public void onImageTouched(VKApiUserFull userData, ImageView image) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.openImage(userData, image);
        } else {
            view.openImage(userData, null);
        }
    }

    @Override
    public void onGetFriendsRequest(int offset) {
        view.setLoading(true);
        if ((offset == 0) && (adapter != null)) {
            adapter.removeAllData();
        }
//        model.getFriends(this::onRequestHandled, offset);
        Thread thread = new Thread(()-> model.getFriends(this::onRequestHandled, offset));
        thread.start();
    }

    @Override
    public void onFriendsExists(VKUsersArray users) {
        if (adapter == null) {
            view.friendsListIsEmpty(false);
            adapter = new FriendsAdapter(users, this::onImageTouched, this::onScrollEnded);
            view.setAdapter(adapter);
        } else {
            adapter.addData(users);
        }
        view.setLoading(false);
    }

    @Override
    public void onRequestHandled(VKApiModel result) {
        if (result instanceof VKUsersArray) {
            VKUsersArray users = ((VKUsersArray)result);
            if (users.size() != 0) {
                onFriendsExists(users);
            } else {
                view.friendsListIsEmpty(true);
            }
        } else {
            view.showToast(R.string.connection_error);
            view.setLoading(false);
        }
    }
}