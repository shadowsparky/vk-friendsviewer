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

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class AuthView extends AppCompatActivity implements IAuthContract.AuthView {
    Button authButton;
    IAuthContract.AuthPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenter(this, new AuthModel(this));
        presenter.onReAuthRequest();
        setContentView(R.layout.activity_auth_view);
        setTitle(getResources().getString(R.string.authorization));
        authButton = findViewById(R.id.AuthButton);
        authButton.setOnClickListener(view-> presenter.onAuthRequest());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                presenter.onAuthSuccess();
            }

            @Override
            public void onError(VKError error) {
                presenter.onAuthError(error.errorCode);
            }
        });
    }

    @Override
    public void showToast(int message_id) {
        Toast.makeText(this, message_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openActivity() {
        Intent i = new Intent(this, FriendsListView.class);
        startActivity(i);
        finish();
    }
}
