package ru.shadowsparky.myfriends.Utils;


import android.graphics.Bitmap;

import com.vk.sdk.api.model.VKApiPhoto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

// FIXME Зависимости андроида не позволяют полностью тестировать этот класс.
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
    public void defaultPhotoParse() {
        String result = cacher.getParsedName(photoWithHD.photo_1280);
        assertEquals(result, "jWEzaw-d8Xs.jpg");
    }

    @Test
    public void specialPhotoParse() {
        String result = cacher.getParsedName("https://vk.com/images/camera_200.png?ava=1");
        assertEquals(result, "camera_200.png?ava=1");
    }

    @Test
    public void errorPhotoUrl() {
        String result = cacher.getParsedName("");
        assertNull(result);
    }
//
//    @Test
//    public void getImage() {
//        cacher.getImage("test.png");
//
//    }
//
//    @Test
//    public void saveImage() {
//        ;
//    }
//
//    @Test
//    public void createCacheFolder() {
//    }
//
//    @Test
//    public void writeToFile() {
//    }
//
//    @Test
//    public void getParsedName() {
//    }
//
//    @Test
//    public void checkFileExists() {
//    }
//
//
}