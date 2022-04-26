package com.oumenreame.viewpager.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.ui.main.handler.HandlerFragment;
import com.oumenreame.viewpager.ui.main.adapter.ViewPager2Adapter;
import com.oumenreame.viewpager.ui.main.download.DownloadFragment;
import com.oumenreame.viewpager.ui.main.recyclerview.RecyclerViewFragment;
import com.oumenreame.viewpager.ui.main.listview.ListViewFragment;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivityViewPager2 extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    ViewPager2 mViewPager;
    ArrayList<Fragment> mFragments;
    BottomNavigationView mBottomNavigationbar;
    CircleIndicator3 mIndicator;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewpager2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);

        initView();
        initFragments();
        initAdapter();
        initListener();
        mIndicator.setViewPager(mViewPager);

        Log.e("Main", "onCreate: ");
    }

    @SuppressLint("NonConstantResourceId")
    private void initListener() {
        mBottomNavigationbar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.show:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.fragmet3:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.handler:
                    mViewPager.setCurrentItem(3);
                    break;
            }
            return false;
        });

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationbar.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationbar.getMenu().findItem(R.id.show).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationbar.getMenu().findItem(R.id.fragmet3).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationbar.getMenu().findItem(R.id.handler).setChecked(true);
                        break;
                }
            }
        });
    }

    private void initAdapter() {
        ViewPager2Adapter viewPagerAdapter = new ViewPager2Adapter(MainActivityViewPager2.this, mFragments);
        mViewPager.setAdapter(viewPagerAdapter);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new RecyclerViewFragment());
        mFragments.add(new ListViewFragment());
        mFragments.add(new DownloadFragment());
        mFragments.add(new HandlerFragment());
    }

    private void initView() {
        mBottomNavigationbar = findViewById(R.id.bottomNavigationbar);
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        mIndicator = findViewById(R.id.indicator);
    }


    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }
}