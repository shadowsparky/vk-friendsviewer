package ru.shadowsparky.myfriends.MVP.Friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.api.model.VKApiUserFull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.MVP.OpenPhoto.OpenPhotoView;
import ru.shadowsparky.myfriends.R;

import static android.view.View.GONE;
import static ru.shadowsparky.myfriends.Utils.Consts.USER_DATA;

public class FriendsListView extends AppCompatActivity implements IFriends.IFriendsListView {
    RecyclerView friendsList;
    SwipeRefreshLayout refresher;
    IFriends.IFriendsListPresenter presenter;
    TextView friendsDontFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list_view);
        friendsList = findViewById(R.id.FriendsList);
        refresher = findViewById(R.id.FriendsListRefresher);
        friendsDontFound = findViewById(R.id.EmptyFriends);
        presenter = new FriendsListPresenter(this);
        refresher.setOnRefreshListener(()-> presenter.getFriendsRequest(0));
        presenter.getFriendsRequest(0);
        presenter.storageChecker();
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
    public void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getResourcesString(int id) {
        return getString(id);
    }

    @Override
    public void openImage(Bundle bundle, VKApiUserFull user) {
        Intent i = new Intent(this, OpenPhotoView.class);
        if (bundle != null) {
            i.putExtra(USER_DATA, user);
            startActivity(i, bundle);
        } else {
            startActivity(i);
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}