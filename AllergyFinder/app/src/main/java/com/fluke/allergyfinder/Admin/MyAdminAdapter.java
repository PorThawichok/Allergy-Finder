package com.fluke.allergyfinder.Admin;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdminAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdminAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DailyDayFragment homeFragment = new DailyDayFragment();
                return homeFragment;
            case 1:
                DailyMonthFragment sportFragment = new DailyMonthFragment();
                return sportFragment;
            case 2:
                DailyYearFragment movieFragment = new DailyYearFragment();
                return movieFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}