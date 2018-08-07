package ru.shadowsparky.myfriends.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImageCacher {
    private final String path = Environment.getExternalStorageDirectory() + "/test/";
    private static ImageCacher instance;

    public static ImageCacher getInstance() {
        if (instance == null) {
            instance = new ImageCacher();
        }
        return instance;
    }
    // fixme
    public Boolean saveImage(String imageName, Bitmap image) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "MyFriendsCache");
        if (!folder.exists())
            folder.mkdir();
    // fixme
        File file = new File(folder.getAbsolutePath(), imageName);
        try {
            file.getParentFile().mkdirs();
            if (!file.createNewFile()) {
                Log.println(Log.DEBUG, "MAIN_TAG", "file don't created " + file.getAbsolutePath());
            } else {
                Log.println(Log.DEBUG, "MAIN_TAG", "FILE CREATED " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            Log.println(Log.DEBUG, "MAIN_TAG", "ERROR: " + e.toString());
        }
        // fixme
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        file = new File(Environment.getExternalStorageDirectory() + File.separator + imageName);
        try {
            //fixme fixme fixme fixme fixme fixme
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
            Log.println(Log.DEBUG, "MAIN_TAG", "Image saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "MAIN_TAG", e.toString());
        }
//            FileOutputStream out = new FileOutputStream(file);
//            image.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.write();
//            out.flush();
//            out.close();
//        return true;
        // fixme
        return false;
    }

    // fixme
    public String getImageName(String fullname) {
        String test []= fullname.split("/");
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
