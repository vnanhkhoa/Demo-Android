package com.oumenreame.viewpager.utils;

import android.Manifest;

public class Constant {
    public static final String MODEL = "model";
    public static final String URL_DOWNLOAD = "http://54.39.180.249/";

    public static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    
    public static final String[] PERMISSION = new String[] {
            READ_STORAGE,
            WRITE_STORAGE,
    };
}
