package com.ownourome.musicmp3.utils.permistion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class RequiredPermission {

    private final Context mContext;
    private final ActivityResultLauncher<String[]> mArrayActivityResultLauncher;

    public RequiredPermission(Context mContext, ActivityResultLauncher<String[]> mArrayActivityResultLauncher) {
        this.mContext = mContext;
        this.mArrayActivityResultLauncher = mArrayActivityResultLauncher;
    }

    public void requestPermission(String... permission) {
        mArrayActivityResultLauncher.launch(permission);
    }

    public boolean isPermissioned(String permission) {
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }

    public void showSettingSystem() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        mContext.startActivity(intent);
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(mContext)
                .setTitle("Permission Required")
                .setMessage("This Permission Is Required For Proper Working Of The App")
                .setNegativeButton("No", (d, i) -> {
                    d.dismiss();
                    Toast.makeText(mContext, "This Permission Is Required For Proper Working Of The App", Toast.LENGTH_SHORT).show();
                    ((Activity) mContext).finish();
                })
                .setPositiveButton("OK", (d, i) -> {
                    showSettingSystem();
                    d.dismiss();
                }).show();
    }
}
