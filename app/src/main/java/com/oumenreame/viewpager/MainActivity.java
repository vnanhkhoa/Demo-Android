package com.oumenreame.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.oumenreame.viewpager.adapter.ViewPagerAdapter;
import com.oumenreame.viewpager.fragment.HomeFragment;
import com.oumenreame.viewpager.fragment.ShowFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ShowFragment());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }
}