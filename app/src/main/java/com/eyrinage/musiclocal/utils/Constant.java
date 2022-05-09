package com.eyrinage.musiclocal.utils;

import android.Manifest;
import android.provider.MediaStore;

public class Constant {

    public static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static final String[] PERMISSIONS_MAIN = new String[]{
            WRITE_STORAGE,
            READ_STORAGE
    };

    public static final String PATH_SONG = MediaStore.Audio.Media.DATA;
    public static final String ARTIST_SONG = MediaStore.Audio.Media.ARTIST;
    public static final String ALBUM_SONG = MediaStore.Audio.Media.ALBUM;
    public static final String TITLE = MediaStore.Audio.Media.TITLE;
    public static final String _ID = MediaStore.Audio.Media._ID;

    public static final String FAVORITE_KEY = "FAVORITE_KEY";
    public static final String FAVORITE = "FAVORITE";

    public static final String CHANNEL_ID = "MP3_CHANNEL";

    public static final String SONG = "SONG";
    public static final String POSITION = "POSITION";
    public static final String LIST_SONG = "LIST SONG";

    public static final int NOTIFICATION_ID = 1;

    public static final String ACTION_MUSIC = "com.eyrinage.musiclocal";
    public static final String ACTION_PLAY = "play";
    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int NEXT = 2;
    public static final int PREVIOUS = 3;
    public static final int CLEAR = -1;

}
