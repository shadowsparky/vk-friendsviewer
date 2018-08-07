package ru.shadowsparky.myfriends.Friends;

import android.os.Bundle;

import com.vk.sdk.api.model.VKUsersArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.shadowsparky.myfriends.FriendsAdapter;
import ru.shadowsparky.myfriends.R;

public class FriendsListView extends AppCompatActivity implements IFriends.IFriendsListView {
    RecyclerView friendsList;
    SwipeRefreshLayout refresher;
    IFriends.IFriendsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list_view);
        friendsList = findViewById(R.id.FriendsList);
        refresher = findViewById(R.id.FriendsListRefresher);
        presenter = new FriendsListPresenter(this);
        refresher.setOnRefreshListener(()->presenter.getFriendsRequest());
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
}