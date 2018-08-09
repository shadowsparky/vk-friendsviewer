package ru.shadowsparky.myfriends.MVP.Auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.MVP.Friends.FriendsListView;
import ru.shadowsparky.myfriends.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class AuthView extends AppCompatActivity implements IAuthContract.IAuthView {
    Button authButton;
    AuthPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenter(this);
        presenter.reAuth();
        setContentView(R.layout.activity_auth_view);
        setTitle(getResources().getString(R.string.authorization));
        authButton = findViewById(R.id.AuthButton);
        authButton.setOnClickListener(view-> presenter.sendAuthRequest(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.authCallback(requestCode, resultCode, data);
    }

    @Override
    public void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openActivity() {
        Intent i = new Intent(this, FriendsListView.class);
        startActivity(i);
        finish();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
