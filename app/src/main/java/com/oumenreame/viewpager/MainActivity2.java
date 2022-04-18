package com.oumenreame.viewpager;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.oumenreame.viewpager.adapter.ViewPagerAdapter;
import com.oumenreame.viewpager.adapter.ViewPagerAdapter2;
import com.oumenreame.viewpager.fragment.Fragment3;
import com.oumenreame.viewpager.fragment.HomeFragment;
import com.oumenreame.viewpager.fragment.ShowFragment;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ViewPager vpg;

    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        vpg = findViewById(R.id.vpg);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ShowFragment());
        fragments.add(new Fragment3());

        ViewPagerAdapter2 viewPagerAdapter = new ViewPagerAdapter2(
                getSupportFragmentManager(),
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments);
        vpg.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (vpg.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            vpg.setCurrentItem(vpg.getCurrentItem() - 1);
        }
    }
}