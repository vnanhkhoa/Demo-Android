package com.oumenreame.viewpager.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class RequiredPermission {
    private final Context mContext;
    private final String[] permissions;
    ActivityResultLauncher<String[]> mArrayActivityResultLauncher;

    public RequiredPermission(Context mContext, String[] permissions, ActivityResultLauncher<String[]> mArrayActivityResultLauncher) {
        this.mContext = mContext;
        this.permissions = permissions;
        this.mArrayActivityResultLauncher = mArrayActivityResultLauncher;

        setPermission();
    }

    public void requestPermission(String... permission) {
        mArrayActivityResultLauncher.launch(permission);
    }

    public void setPermission() {
        ArrayList<String> arr = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissioned(permission)) {
                arr.add(permission);
            }
        }

        if (arr.size() > 0) {
            requestPermission(arr.toArray(new String[0]));
        }
    }

    public boolean isPermissioned(String permission) {
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void showSettingSystem() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        mContext.startActivity(intent);
    }

    public void showAlert(String permission) {
        new MaterialAlertDialogBuilder(mContext)
                .setTitle("Permission Required")
                .setMessage("This Permission Is Required For Proper Working Of The App")
                .setNegativeButton("No Thank", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("Ask Permission Again", (d, i) -> {
                    requestPermission(permission);
                    d.dismiss();
                }).show();
    }
}
