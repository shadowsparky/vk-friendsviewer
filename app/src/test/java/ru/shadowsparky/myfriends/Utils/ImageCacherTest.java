package ru.shadowsparky.myfriends.Utils;
import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKApiPhoto;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

public class ImageCacherTest {
    VKApiPhoto photoWithHD = new VKApiPhoto();
    VKApiPhoto photoWithoutHD = new VKApiPhoto();
    ImageCacher cacher = new ImageCacher();
    Bitmap picture;
    @Before
    public void setUp() throws Exception {
        photoWithHD.photo_1280 = "https://sun1-17.userapi.com/c635106/v635106426/88ec/jWEzaw-d8Xs.jpg";
        photoWithoutHD.photo_1280 = "";
        photoWithoutHD.photo_604 = "https://sun1-17.userapi.com/c635106/v635106426/88ec/jWEzaw-d8Xs.jpg";
    }

    @Test
    public void hdPhotoChecker() {
        String result = cacher.hdPhotoChecker(photoWithHD);
        assertNotEquals(result, "");
        result = cacher.hdPhotoChecker(photoWithoutHD);
        assertNotEquals(result, "");
    }

    @Test
    public void getImage() {
        picture = cacher.getImage("5ND-FPyQe_U.jpg");
    }

    @Test
    public void saveImage() {
        //;
    }

    @Test
    public void createCacheFolder() {
    }

    @Test
    public void writeToFile() {
    }

    @Test
    public void getParsedName() {
    }

    @Test
    public void checkFileExists() {
    }


}