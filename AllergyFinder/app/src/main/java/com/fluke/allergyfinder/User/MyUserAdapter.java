package com.fluke.allergyfinder.User;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fluke.allergyfinder.Admin.DailyDayFragment;
import com.fluke.allergyfinder.Admin.DailyMonthFragment;
import com.fluke.allergyfinder.Admin.DailyYearFragment;

public class MyUserAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyUserAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SearchFragment1 homeFragment = new SearchFragment1();
                return homeFragment;
            case 1:
                SearchFragment2 sportFragment = new SearchFragment2();
                return sportFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}