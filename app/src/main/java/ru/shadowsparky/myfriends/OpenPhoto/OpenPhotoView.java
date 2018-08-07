package ru.shadowsparky.myfriends.OpenPhoto;

import androidx.appcompat.app.AppCompatActivity;
import ru.shadowsparky.myfriends.ICallbacks;
import ru.shadowsparky.myfriends.ImageDownloader;
import ru.shadowsparky.myfriends.R;

import android.os.Bundle;
import android.widget.ImageView;

public class OpenPhotoView extends AppCompatActivity {
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo_view);
        photo = findViewById(R.id.User_Photo);
        ICallbacks.IDownloadImage callback = (image) -> {
            photo.setImageBitmap(image);
        };
        String url = getIntent().getStringExtra("URL");
//        = savedInstanceState.getString("URL");
        ImageDownloader downloader = new ImageDownloader(callback);
        downloader.execute(url);
    }
}
