package com.daily.baselibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * author: dlx
 * created on: 2017/3/17 9:42
 * description:ViewPager中嵌套Fragment基础适配器
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private String[] titles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments == null || fragments.isEmpty())
            return null;
        else
            return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null || fragments.isEmpty())
            return 0;
        else
            return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0)
            return titles[position];
        else return null;
    }
}
