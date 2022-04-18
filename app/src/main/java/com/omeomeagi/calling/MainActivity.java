package com.omeomeagi.calling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.omeomeagi.calling.fragment.Fragment1;
import com.omeomeagi.calling.fragment.Fragment2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    TextView txtPhone;
    private String TAG = "MainActivity";
    int i = 0;
    ArrayList<Fragment> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arr = new ArrayList<>();
        arr.add(new Fragment1());
        arr.add(new Fragment2());
//        txtPhone = findViewById(R.id.txtPhone);
//
//        //start reading here
//        Intent intent = getIntent();
//        if(intent.getData()!=null){
//            String phoneNumber = intent.getData().toString();
//            txtPhone.setText(phoneNumber);//contains tel:phone_no
//            /// do what you like here
//        }
        i = (savedInstanceState == null) ? 0 : savedInstanceState.getInt("i");
        initFragment();
        Log.e(TAG, "onCreate: ");
    }

    public void initFragment(){
        if (i != 0) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.layout,arr.get(i));
        transaction.add(R.id.layout,new Fragment1("abc"));
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("i",i);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    public void click(View view) {
        if (getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("Fragment2"))
            arr.add(1,getSupportFragmentManager().getFragments().get(0));
        if (arr.get(1).isResumed()) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("a","Activity send Data");
        arr.get(1).setArguments(bundle);
        transaction.add(R.id.layout,arr.get(1));
//        transaction.addToBackStack(arr.get(0).getClass().getSimpleName());
        transaction.commit();
    }

    public void click1(View view) {
//        FragmentTransaction transaction = .beginTransaction();
////        transaction.remove(arr.get(1));
////        transaction.commit();
//        transaction.
        getSupportFragmentManager().popBackStack();

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
        Log.e(TAG, "onResume: ");
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
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }


}