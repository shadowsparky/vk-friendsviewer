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
    private final String path = Environment.getExternalStorageDirectory() + "/test/";
    private static ImageCacher instance;

    public static ImageCacher getInstance() {
        if (instance == null) {
            instance = new ImageCacher();
        }
        return instance;
    }

    public Boolean saveImage(String imageName, Bitmap image) {
        new File(path).mkdir();
        File file = new File(path + imageName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.println(Log.DEBUG, "MAIN_TAG", "ERROR: " + e.toString());
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        file = new File(Environment.getExternalStorageDirectory() + File.separator + imageName);
        try {
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

        return false;
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
