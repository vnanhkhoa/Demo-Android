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

    public static final String CHANNEL_ID = "MP3_CHANNEL";

    public static final String SONG_LIST = "SONG LIST";
    public static final String SONG_DATA_EXTRA = "SONG DATA";
    public static final String POSITION = "POSITION";
    public static final String SONG_ID = "SONG ID";

    public static final int NOTIFICATION_ID = 1;

    public static final String ACTION_INTENT_MUSIC = "com.ownourome.musicmp3";
    public static final String ACTION_PLAY = "ACTION PLAY";
    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int NEXT = 2;
    public static final int PREVIOUS = 3;
    public static final int CLEAR = -1;
    public static final int START_ANIMATE = 4;

    public static final long TIME_DELAY = 10000;
    public static final int ONE_SECOND = 1000;


    public static final String TIME_FORMAT = "%02d:%02d";
    public static final String TIME_START = "00:00";



}
