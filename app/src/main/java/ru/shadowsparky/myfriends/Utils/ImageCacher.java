package ru.shadowsparky.myfriends.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImageCacher {
    private final String path = Environment.getExternalStorageDirectory().toString() + File.separator + "MyFriendsCache" + File.separator;
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
            image.compress(Bitmap.CompressFormat.PNG, 0, bytes);
            try {
                writeToFile(file, bytes);
                return true;
            } catch (IOException e) {
                Log.println(Log.DEBUG, "MAIN_TAG", "Exception on image save: " + e.toString());
            }
        }
        return false;
    }

    public Boolean createCacheFolder() {
        File folder = new File(path, "MyFriendsCache");
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
        String result = fullname.split("/")[3];
        if (result.equals("images")) {
            result = fullname.split("/")[4];
        } else {
            try {
                result = fullname.split("/")[6];
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
