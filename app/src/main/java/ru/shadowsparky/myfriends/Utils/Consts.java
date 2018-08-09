package ru.shadowsparky.myfriends.Utils;

import android.os.Environment;

import java.io.File;

public class Consts {
    public static final String MAIN_TAG = "MAIN_TAG";
    public static final int VK_SPECIAL_PATH = 3;
    public static final int VK_SPECIAL_NAME = 4;
    public static final int VK_DEFAULT_NAME = 6;
    public static final int PNG_QUALITY = 0;
    public static final String URL_SEPARATOR = "/";
    public static final String DEFAULT_CACHE_FOLDER = "MyFriendsCache";
    public static final String VK_SPECIAL_FOLDER = "images";
    public static final String DEFAULT_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + DEFAULT_CACHE_FOLDER + File.separator;
    public static final String USER_DATA = "USER_DATA";
    /*VK USED METHODS*/
    public static final String FRIENDS_GET_METHOD = "friends.get";
    public static final String PHOTOS_GET_METHOD = "photos.get";
}
