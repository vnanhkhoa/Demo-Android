package com.oumenreame.viewpager.service;

import static com.oumenreame.viewpager.service.DownloadHandler.DONE;
import static com.oumenreame.viewpager.service.DownloadHandler.SEND_PROGRESS;
import static com.oumenreame.viewpager.service.DownloadHandler.START;
import static com.oumenreame.viewpager.service.DownloadHandler.STATUS;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;

public class DownloadThread implements Runnable {

    DownloadHandler downloadHandler;
    long time;

    public DownloadThread(DownloadHandler downloadHandler, long time) {
        this.downloadHandler = downloadHandler;
        this.time = time;
    }

    @Override
    public void run() {
        downloadHandler.sendEmptyMessage(START);

        for (int i = 1; i < 31; i++) {
            SystemClock.sleep(time);
            Message message = new Message();
            message.arg1 = i;
            message.what = SEND_PROGRESS;
            downloadHandler.sendMessage(message);
        }

        Message messageStatus = new Message();
        messageStatus.what = DONE;
        Bundle bundle = new Bundle();
        bundle.putBoolean(STATUS, true);
        messageStatus.setData(bundle);
        downloadHandler.sendMessage(messageStatus);
    }
}
