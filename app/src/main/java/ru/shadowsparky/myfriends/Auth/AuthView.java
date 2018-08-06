package ru.shadowsparky.myfriends.Auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.FriendsListView;
import ru.shadowsparky.myfriends.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

public class AuthView extends AppCompatActivity implements IAuthContract.IAuthView {
    Button authButton;
    AuthPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenter(this);
        presenter.reAuth();
        setContentView(R.layout.activity_auth_view);
        setTitle("Авторизация");
        authButton = findViewById(R.id.AuthButton);
        authButton.setOnClickListener(view-> VKSdk.login(this, VKScope.FRIENDS));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.checkAuth(requestCode, resultCode, data);
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
}
