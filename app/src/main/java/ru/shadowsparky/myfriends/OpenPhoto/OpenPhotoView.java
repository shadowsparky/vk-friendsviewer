package ru.shadowsparky.myfriends.OpenPhoto;

import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageDownloader;
import ru.shadowsparky.myfriends.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKPhotoArray;

import org.json.JSONException;

public class OpenPhotoView extends AppCompatActivity implements IOpenPhoto.IOpenPhotoView {
    ImageView photo;
    TextView loadingError;
    VKApiUserFull userData;
    Menu photoMenu;
    IOpenPhoto.IOpenPhotoPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo_view);
        photo = findViewById(R.id.User_Photo);
        loadingError = findViewById(R.id.User_Photo_Not_Loaded);
        userData = getIntent().getParcelableExtra("USER_DATA");
        presenter = new OpenPhotoPresenter(this);
        presenter.getPhotoRequest(userData.getId());
    }

    @Override
    public void setImage(Bitmap image) {
        photo.setImageBitmap(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.open_photo_menu, menu);
        photoMenu = menu;
        return true;
    }

    @Override
    public void loadingError() {
        loadingError.setVisibility(View.VISIBLE);
        photo.setVisibility(View.GONE);
    }

    @Override
    public void setMenuData(int likeCount, int tagCount, int commentsCount) {
        if (photoMenu != null){
            MenuItem like = photoMenu.findItem(R.id.LikeCount);
            like.setTitle(like.getTitle() + String.valueOf(likeCount));
            MenuItem tag = photoMenu.findItem(R.id.TagCount);
            tag.setTitle(tag.getTitle() + String.valueOf(tagCount));
            MenuItem comment = photoMenu.findItem(R.id.CommentsCount);
            comment.setTitle(comment.getTitle() + String.valueOf(commentsCount));
        }
    }
}
