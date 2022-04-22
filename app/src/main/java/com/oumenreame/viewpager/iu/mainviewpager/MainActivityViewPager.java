package com.oumenreame.viewpager.iu.mainviewpager;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.fragment.DownloadFragment;
import com.oumenreame.viewpager.fragment.RecyclerViewFragment;
import com.oumenreame.viewpager.fragment.ListViewFragment;
import com.oumenreame.viewpager.iu.mainviewpager.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivityViewPager extends AppCompatActivity {
    ViewPager vpg;

    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        vpg = findViewById(R.id.vpg);

        fragments = new ArrayList<>();
        fragments.add(new RecyclerViewFragment());
        fragments.add(new ListViewFragment());
        fragments.add(new DownloadFragment());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
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