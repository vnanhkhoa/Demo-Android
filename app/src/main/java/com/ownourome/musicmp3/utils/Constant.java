package com.ownourome.musicmp3.utils;

import android.Manifest;

public class Constant {

    public static final String URL_SONG = "http://api.mp3.zing.vn/api/streaming/audio/:idSong/128";

    public static final String URL_BASE = "http://mp3.zing.vn";

    public static final String VALUE_SUCCESS = "Success";
    public static final String DATABASE_NAME = "song-database";

    public static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static final String[] PERMISSIONS_MAIN = new String[]{
            WRITE_STORAGE,
            READ_STORAGE
    };

    public static final String NAME_FOLDER = "SongMP3";
    public static final String SUB_FILE = ".mp3";

}
