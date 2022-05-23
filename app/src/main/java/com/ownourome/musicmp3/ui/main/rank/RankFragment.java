package com.ownourome.musicmp3.ui.main.rank;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.ui.adapter.PageFragmentAdapter;
import com.ownourome.musicmp3.ui.main.rank.usuk.UsUkFragment;
import com.ownourome.musicmp3.ui.main.rank.vietnam.VietNamFragment;

import java.util.ArrayList;

public class RankFragment extends Fragment {

    private static int page = -1;

    @SuppressLint("StaticFieldLeak")
    private TabLayout mTabLayoutCountry;
    private ViewPager2 mViewPagerCountry;
    private ArrayList<Fragment> mFragments;


    public RankFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        mFragments.add(new VietNamFragment());
        mFragments.add(new UsUkFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initListener();
    }

    private void initListener() {
        mTabLayoutCountry.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerCountry.setCurrentItem(tab.getPosition());
                page = mViewPagerCountry.getCurrentItem();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPagerCountry.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayoutCountry.selectTab(mTabLayoutCountry.getTabAt(position));
                page = position;
            }
        });
    }

    private void initViews(View view) {
//        Button mBtnSearch = view.findViewById(R.id.btnSearch);
        mTabLayoutCountry = view.findViewById(R.id.tabLayoutCountry);
        mViewPagerCountry = view.findViewById(R.id.viewPagerCountry);
        if (page != -1) {
            mFragments.get(page).onResume();
        }
        PageFragmentAdapter mPageFragmentAdapter = new PageFragmentAdapter(requireActivity(), mFragments);
        mViewPagerCountry.setAdapter(mPageFragmentAdapter);
        page = mViewPagerCountry.getCurrentItem();
    }
}