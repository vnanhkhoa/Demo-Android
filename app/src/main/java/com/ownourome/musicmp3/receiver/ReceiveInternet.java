package com.ownourome.musicmp3.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.ownourome.musicmp3.utils.callback.CheckInternetCallback;

public class ReceiveInternet extends BroadcastReceiver {

    private final CheckInternetCallback checkInternetCallback;

    public ReceiveInternet(CheckInternetCallback checkInternetCallback) {
        this.checkInternetCallback = checkInternetCallback;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOnline(context)) {
            checkInternetCallback.isOnline();
        } else {
            checkInternetCallback.isOffline();
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager conn = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conn == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = conn.getActiveNetwork();
            if (network == null) {
                return false;
            }

            NetworkCapabilities capabilities = conn.getNetworkCapabilities(network);
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
    }
}
