package com.omeomeagi.testactivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String TAG = "1";
    int a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: ");
    }

    public void click(View view) {
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + "12368"));
        startActivity(new Intent(MainActivity.this,MainActivity2.class));

//        startActivity(Intent.createChooser(callIntent,null));
//        Intent si = new Intent(Intent.ACTION_SEND);
//        si.setType("message/rfc822");
//        si.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@tutlane.com"});
//        si.putExtra(Intent.EXTRA_SUBJECT, "Welcome to Tutlane");
//        si.putExtra(Intent.EXTRA_TEXT, "Hi Guest, Welcome to Tutlane Tutorial Site");
//        startActivity(Intent.createChooser(si,"Choose Mail App"));
    }

    public void click1(View view) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ++a;
        Log.e(TAG, "onResume: "+a);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: "+a);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        a = savedInstanceState.getInt("a");
        Log.e(TAG, "onRestoreInstanceState: "+a);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("a",a);
        Log.e(TAG, "onSaveInstanceState: "+a);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//
//    }
}