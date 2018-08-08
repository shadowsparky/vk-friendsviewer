package ru.shadowsparky.myfriends.OpenPhoto;

import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageDownloader;
import ru.shadowsparky.myfriends.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiUserFull;

public class OpenPhotoView extends AppCompatActivity {
    ImageView photo;
    VKApiUserFull userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo_view);
        photo = findViewById(R.id.User_Photo);
//        ICallbacks.IDownloadImage callback = (image) -> photo.setImageBitmap(image);
        userData = getIntent().getParcelableExtra("USER_DATA");
//        Log.println(Log.DEBUG, "MAIN_TAG", userData.first_name);
//        ImageDownloader downloader = new ImageDownloader(callback);
//        downloader.execute(url);
    }
}
