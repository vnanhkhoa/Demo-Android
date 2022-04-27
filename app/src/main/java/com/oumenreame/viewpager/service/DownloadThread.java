package com.oumenreame.viewpager.service;

import static com.oumenreame.viewpager.service.DownloadHandler.DONE;
import static com.oumenreame.viewpager.service.DownloadHandler.SEND_PROGRESS;
import static com.oumenreame.viewpager.service.DownloadHandler.START;
import static com.oumenreame.viewpager.service.DownloadHandler.STATUS;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloadThread implements Runnable {

    DownloadHandler downloadHandler;
    long time;
    String fileName;
    String urlVideo;

    public DownloadThread(DownloadHandler downloadHandler, long time, String urlVideo,String fileName) {
        this.downloadHandler = downloadHandler;
        this.time = time;
        this.urlVideo = urlVideo;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        downloadHandler.sendEmptyMessage(START);

        boolean status = true;

        try {
            URL url = new URL(urlVideo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int length = connection.getContentLength();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            file = new File(file, fileName);

            FileOutputStream output = new FileOutputStream(file);

            byte[] data = new byte[4096];
            long total = 0;
            int count;
            while ((count = is.read(data)) != -1) {
                total += count;
                if (length > 0) {
                    Message message = new Message();
                    message.arg1 = (int) (total * 100 / length);
                    message.what = SEND_PROGRESS;
                    downloadHandler.sendMessage(message);
                }
                output.write(data, 0, count);
            }
            output.close();
            is.close();
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            status = false;
            Log.e("LOI", "run: "+e.getMessage());
            e.printStackTrace();
        }

        Message messageStatus = new Message();
        messageStatus.what = DONE;
        Bundle bundle = new Bundle();
        bundle.putBoolean(STATUS, status);
        messageStatus.setData(bundle);
        downloadHandler.sendMessage(messageStatus);
    }

}
