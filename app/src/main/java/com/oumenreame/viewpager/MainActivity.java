package com.oumenreame.viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.oumenreame.viewpager.adapter.ViewPagerAdapter;
import com.oumenreame.viewpager.fragment.Fragment3;
import com.oumenreame.viewpager.fragment.HomeFragment;
import com.oumenreame.viewpager.fragment.ShowFragment;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    ViewPager2 mViewPager;
    ArrayList<Fragment> mFragments;
    BottomNavigationView mBottomNavigationbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);

        mBottomNavigationbar = findViewById(R.id.bottomNavigationbar);
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);

        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new ShowFragment());
        mFragments.add(new Fragment3());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, mFragments);
        mViewPager.setAdapter(viewPagerAdapter);
//        mViewPager
        mBottomNavigationbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.show:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.fragmet3:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mBottomNavigationbar.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationbar.getMenu().findItem(R.id.show).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationbar.getMenu().findItem(R.id.fragmet3).setChecked(true);
                        break;
                }
            }
        });

        CircleIndicator3 indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        Log.e("Main", "onCreate: ");
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