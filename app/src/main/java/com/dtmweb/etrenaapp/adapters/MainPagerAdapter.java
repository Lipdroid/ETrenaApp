package com.dtmweb.etrenaapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.fragments.CartFragment;
import com.dtmweb.etrenaapp.fragments.CategoryFragment;
import com.dtmweb.etrenaapp.fragments.FavouriteFragment;
import com.dtmweb.etrenaapp.fragments.HomeFragment;
import com.dtmweb.etrenaapp.fragments.ProfileFragment;

/**
 * Created by mdmunirhossain on 5/4/18.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        /** Show a Fragment based on the position of the current screen */
        if (position == Constants.HOME) {
            return new HomeFragment();
        } else if(position == Constants.CATEGORY) {
            return new CategoryFragment();
        }else if(position == Constants.FAVOURITE) {
            return new FavouriteFragment();
        }else if(position == Constants.CART) {
            return new CartFragment();
        }else if(position == Constants.PROFILE) {
            return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return 5;
    }
}
