package com.scottlindley.farmgroceryapp.FarmActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class FarmPagerAdapter extends FragmentPagerAdapter {
    public FarmPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Info";
            case 1: return "Crops";
            case 2: return "Likes";
            default: return null;
        }
    }
}
