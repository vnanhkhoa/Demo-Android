package com.oumenreame.viewpager.task;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoad extends AsyncTask<String,Integer,Boolean> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(@NonNull String... strings) {
        String urlVideo = strings[0];
        try {
            URL url=new URL(urlVideo);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int leng = connection.getContentLength();

            InputStream is= connection.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"UTF-8");
            BufferedReader br=new BufferedReader(isr);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            file = new File(file,"video.mp4");

            FileOutputStream output = new FileOutputStream(file);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = is.read(data)) != -1) {
                if (isCancelled()) {
                    is.close();
                    return null;
                }
                total += count;
                if (leng > 0){
                    publishProgress((int) (total * 100 / leng));
                }
                output.write(data, 0, count);
            }
            output.close();
            is.close();
            br.close();
            connection.disconnect();
            return true;
        } catch (Exception e) {
            Log.e("LOI", "doInBackground: "+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
