package com.tungthanh1497.moozik.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tungthanh1497.moozik.fragments.HotTrendFragment;
import com.tungthanh1497.moozik.fragments.MusicTypeFragment;
import com.tungthanh1497.moozik.fragments.OfflineFragment;
import com.tungthanh1497.moozik.fragments.PlayListFragment;
import com.tungthanh1497.moozik.fragments.SearchFragment;

public class TabPagerMainAdapter extends FragmentPagerAdapter {
    int tabCount;

    public TabPagerMainAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MusicTypeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new HotTrendFragment();
            case 3:
                return new OfflineFragment();
            case 4:
                return new PlayListFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
