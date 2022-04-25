package com.oumenreame.viewpager.task;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class DownloadHandler extends Handler {

    public static final int DONE = 0;
    public static final int START = 1;
    public static final int SEND_PROGRESS = 2;
    public static final String STATUS = "status";

    ProgressBar progressBar;
    TextView tv;
    View view;
    String name;

    public DownloadHandler(ProgressBar progressBar, TextView tv,View view,String name) {
        this.progressBar = progressBar;
        this.tv = tv;
        this.view = view;
        this.name = name;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case DONE:
                Bundle bundle = msg.getData();
                boolean status = bundle.getBoolean(STATUS);
                String s = (status) ? "Done" : "Failed";
                Snackbar.make(view,s, BaseTransientBottomBar.LENGTH_SHORT).show();
                break;
            case START:
                this.progressBar.setProgress(0);
                this.tv.setText("0%");
                break;
            case SEND_PROGRESS:
                this.progressBar.setProgress(msg.arg1);
                this.tv.setText(msg.arg1+"%");
                Log.e("SEND", name+" -> "+msg.arg1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + msg.what);
        }
    }
}
