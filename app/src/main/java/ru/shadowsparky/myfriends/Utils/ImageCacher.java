package ru.shadowsparky.myfriends.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCacher {
    public static final int VK_SPECIAL_PATH = 3;
    public static final int VK_SPECIAL_NAME = 4;
    public static final int VK_DEFAULT_NAME = 6;
    public static final int PNG_QUALITY = 0;
    public static final String URL_SEPARATOR = "/";
    public static final String DEFAULT_CACHE_FOLDER = "MyFriendsCache";
    public static final String VK_SPECIAL_FOLDER = "images";
    private final String path = Environment.getExternalStorageDirectory().toString() + File.separator + DEFAULT_CACHE_FOLDER + File.separator;
    private static ImageCacher instance;

    public static ImageCacher getInstance() {
        if (instance == null) {
            instance = new ImageCacher();
        }
        return instance;
    }

    public Boolean saveImage(String imageName, Bitmap image) {
        if (createCacheFolder()) {
            File file = new File(path, imageName);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, bytes);
            try {
                writeToFile(file, bytes);
                return true;
            } catch (IOException e) {
                Log.println(Log.DEBUG, "MAIN_TAG", "an error occurred while saving the image: " + e.toString());
            }
        }
        return false;
    }

    public Boolean createCacheFolder() {
        File folder = new File(path, DEFAULT_CACHE_FOLDER);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public void writeToFile(File file, ByteArrayOutputStream bytes) throws IOException {
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(bytes.toByteArray());
        fo.flush();
        fo.close();
        Log.println(Log.DEBUG, "MAIN_TAG", "Image saved: " + file.getAbsolutePath());

    }

    // fixme
    // на самом деле тут все шикарно.
    public String getImageName(String fullname) {
        String result = fullname.split(URL_SEPARATOR)[VK_SPECIAL_PATH];
        if (result.equals(VK_SPECIAL_FOLDER)) {
            result = fullname.split(URL_SEPARATOR)[VK_SPECIAL_NAME];
        } else {
            try {
                result = fullname.split(URL_SEPARATOR)[VK_DEFAULT_NAME];
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.println(Log.DEBUG, "MAIN_TAG", e.toString() + " - " + fullname);
            }
        }
        return result;
    }

    public Bitmap getImage(String imageName) {
        File file = new File(path + imageName);
        Bitmap bitmap = null;
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.println(Log.DEBUG, "MAIN_TAG", "Image loaded: " + file.getAbsolutePath());
        }
        return bitmap;
    }

    public Boolean checkFileExists(String imageName) {
        File file = new File(path + imageName);
        return file.exists();
    }
}
