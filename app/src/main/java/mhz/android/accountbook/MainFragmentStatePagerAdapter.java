package mhz.android.accountbook;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import mhz.android.accountbook.fragments.ItemListFragment;
import mhz.android.accountbook.fragments.MainViewFragment;

/**
 * Created by MHz on 2015/11/01.
 */
public class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    public MainFragmentStatePagerAdapter( FragmentManager fm ) {
        super( fm );
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MainViewFragment();
            default:
                return new ItemListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position + 1);
    }
}
