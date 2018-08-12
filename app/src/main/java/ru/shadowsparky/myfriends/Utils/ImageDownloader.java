package ru.shadowsparky.myfriends.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static ru.shadowsparky.myfriends.Utils.Consts.MAIN_TAG;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    ICallbacks.IDownloadImage callback;
    String url;

    public ImageDownloader(ICallbacks.IDownloadImage callback) {
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            url = urls[0];
            URL urlConnection = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, MAIN_TAG, e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        callback.onImageDownloaded(image, url);
    }
}
