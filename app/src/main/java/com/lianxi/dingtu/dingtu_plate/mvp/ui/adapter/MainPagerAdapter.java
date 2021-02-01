package com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lianxi.dingtu.dingtu_plate.mvp.ui.fragment.BaseFragment;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/12/10 10:09
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    public MainPagerAdapter(FragmentManager fm, List<BaseFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
