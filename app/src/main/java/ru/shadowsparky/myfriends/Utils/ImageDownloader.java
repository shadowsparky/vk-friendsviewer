package ru.shadowsparky.myfriends.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    ICallbacks.IDownloadImage callback;

    public ImageDownloader(ICallbacks.IDownloadImage callback) {
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
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
            Log.println(Log.DEBUG, "MAIN_TAG", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        callback.downloadImageCallback(image);
    }
}
