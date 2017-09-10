package com.example.mrz.newproject.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<Fragment> pagerFragments;
    public FragmentPagerAdapter(FragmentManager fm,List<Fragment> pagerFragments) {
        super(fm);
        this.pagerFragments=pagerFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return pagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return pagerFragments.size();
    }
}
