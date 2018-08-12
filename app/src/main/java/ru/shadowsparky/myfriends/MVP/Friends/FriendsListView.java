package ru.shadowsparky.myfriends.MVP.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.api.model.VKApiUserFull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.MVP.OpenPhoto.OpenPhotoView;
import ru.shadowsparky.myfriends.R;

import static android.view.View.GONE;
import static ru.shadowsparky.myfriends.Utils.Consts.USER_DATA;

public class FriendsListView extends AppCompatActivity implements IFriends.FriendsListView {
    RecyclerView friendsList;
    SwipeRefreshLayout refresher;
    IFriends.FriendsListPresenter presenter;
    TextView friendsDontFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list_view);
        friendsList = findViewById(R.id.FriendsList);
        refresher = findViewById(R.id.FriendsListRefresher);
        friendsDontFound = findViewById(R.id.EmptyFriends);
        presenter = new FriendsListPresenter(this, new FriendsListModel());
        refresher.setOnRefreshListener(()-> presenter.onGetFriendsRequest(0));
        presenter.onGetFriendsRequest(0);
    }

    @Override
    public void setLoading(boolean result) {
        refresher.setRefreshing(result);
    }

    @Override
    public void setAdapter(FriendsAdapter adapter) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        friendsList.setLayoutManager(llm);
        friendsList.setHasFixedSize(false);
        friendsList.setAdapter(adapter);
    }

    @Override
    public void friendsListIsEmpty(boolean result) {
        if (result) {
            refresher.setVisibility(GONE);
            friendsDontFound.setVisibility(View.VISIBLE);
        } else {
            friendsDontFound.setVisibility(GONE);
        }
    }

    @Override
    public void showToast(int message_id) {
        Toast.makeText(this, message_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openImage(VKApiUserFull user, ImageView image) {
        Intent i = new Intent(this, OpenPhotoView.class);
        if (image != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, image, getString(R.string.friends_image_transition));
            i.putExtra(USER_DATA, user);
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }
    }
}