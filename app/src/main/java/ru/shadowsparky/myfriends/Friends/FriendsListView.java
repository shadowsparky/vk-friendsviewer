package ru.shadowsparky.myfriends.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.shadowsparky.myfriends.Adapter.FriendsAdapter;
import ru.shadowsparky.myfriends.OpenPhoto.OpenPhotoView;
import ru.shadowsparky.myfriends.R;

import static android.view.View.GONE;

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
    public void openImage(Bundle bundle, String url) {
        Intent i = new Intent(this, OpenPhotoView.class);
        if (bundle != null) {
            i.putExtra("URL", url);
            startActivity(i, bundle);
        } else {
            startActivity(i);
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }
}