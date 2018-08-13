package ru.shadowsparky.myfriends.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.vk.sdk.api.model.VKApiPhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static ru.shadowsparky.myfriends.Utils.Consts.DEFAULT_CACHE_FOLDER;
import static ru.shadowsparky.myfriends.Utils.Consts.DEFAULT_PATH;
import static ru.shadowsparky.myfriends.Utils.Consts.MAIN_TAG;
import static ru.shadowsparky.myfriends.Utils.Consts.PNG_QUALITY;
import static ru.shadowsparky.myfriends.Utils.Consts.URL_SEPARATOR;
import static ru.shadowsparky.myfriends.Utils.Consts.VK_DEFAULT_NAME;
import static ru.shadowsparky.myfriends.Utils.Consts.VK_SPECIAL_FOLDER;
import static ru.shadowsparky.myfriends.Utils.Consts.VK_SPECIAL_NAME;
import static ru.shadowsparky.myfriends.Utils.Consts.VK_SPECIAL_PATH;

public class ImageCacher {

    public void saveImageToFile(String imageName, Bitmap image) {
        if (!checkFileExists(getParsedName(imageName))) {
            saveImage(getParsedName(imageName), image);
        }
    }

    public String hdPhotoChecker(VKApiPhoto photo) {
        String url = photo.photo_1280;
        if (url.equals("")) {
            url = photo.photo_604;
        }
        return url;
    }

    public void userWithEmptyPhotoChecker(String url, ICallbacks.IDownloadImage callback) {
        if (url != null) {
            cachedPhotoChecker(url, callback);
        }
    }

    public void cachedPhotoChecker(String imageurl, ICallbacks.IDownloadImage callback) {
        if (checkFileExists(getParsedName(imageurl))) {
            callback.onImageDownloaded(getImage(getParsedName(imageurl)), imageurl);
            Log.println(Log.DEBUG, MAIN_TAG, "Cached image loaded");
        } else {
            ImageDownloader downloader = new ImageDownloader(callback);
            downloader.execute(imageurl);
            Log.println(Log.DEBUG, MAIN_TAG, "Image downloaded");
        }
    }

    public void saveImage(String imageName, Bitmap image) {
        Thread t = new Thread(()->{
            createCacheFolder();
            File file = new File(DEFAULT_PATH, imageName);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, bytes);
            try {
                writeToFile(file, bytes);
            } catch (IOException e) {
                Log.println(Log.DEBUG, MAIN_TAG, "an error occurred while saving the image: " + e.toString());
            }
        });
        t.start();
    }

    public void createCacheFolder() {
        File folder = new File(DEFAULT_PATH, DEFAULT_CACHE_FOLDER);
        if (!folder.exists())
            folder.getParentFile().mkdirs();
    }

    public void writeToFile(File file, ByteArrayOutputStream bytes) throws IOException {
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(bytes.toByteArray());
        fo.flush();
        fo.close();
    }

    // fixme
    // на самом деле тут все шикарно.
    public String getParsedName(String fullname) {
        String result = null;
        try {
            result = fullname.split(URL_SEPARATOR)[VK_SPECIAL_PATH];
            if (result.equals(VK_SPECIAL_FOLDER)) {
                result = fullname.split(URL_SEPARATOR)[VK_SPECIAL_NAME];
            } else {
                result = fullname.split(URL_SEPARATOR)[VK_DEFAULT_NAME];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.println(Log.DEBUG, MAIN_TAG, "file not recognized " + e.toString() + " - " + fullname);
        }
        return result;
    }

    public Bitmap getImage(String imageName) {
        File file = new File(DEFAULT_PATH + imageName);
        Bitmap bitmap = null;
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return bitmap;
    }

    public Boolean checkFileExists(String imageName) {
        File file = new File(DEFAULT_PATH + imageName);
        return file.exists();
    }
}
