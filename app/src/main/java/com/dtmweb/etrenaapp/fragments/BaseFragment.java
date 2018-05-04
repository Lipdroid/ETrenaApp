package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.MainPagerAdapter;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.utils.GlobalUtils;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    private Context mContext = null;

    private ImageView btn_home = null;
    private ImageView btn_category = null;
    private ImageView btn_fav = null;
    private ImageView btn_cart = null;
    private ImageView btn_profile = null;

    private ViewPager mViewPager = null;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_base, container, false);
        mContext = getActivity();
        //findViews
        btn_home = (ImageView) root.findViewById(R.id.btn_home);
        btn_category = (ImageView) root.findViewById(R.id.btn_category);
        btn_fav = (ImageView) root.findViewById(R.id.btn_fav);
        btn_cart = (ImageView) root.findViewById(R.id.btn_cart);
        btn_profile = (ImageView) root.findViewById(R.id.btn_profile);
        mViewPager = (ViewPager) root.findViewById(R.id.viewpager);
        //add listeners
        btn_home.setOnClickListener(this);
        btn_category.setOnClickListener(this);
        btn_fav.setOnClickListener(this);
        btn_cart.setOnClickListener(this);
        btn_profile.setOnClickListener(this);

        //set the profile or shop icon
        if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
            btn_profile.setImageResource(R.drawable.shop_unselected);
        }


        /** set the adapter for ViewPager */
        mViewPager.setAdapter(new MainPagerAdapter(
                getChildFragmentManager()));

        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                changeTabState(Constants.HOME);
                mViewPager.setCurrentItem(Constants.HOME);
                break;
            case R.id.btn_category:
                changeTabState(Constants.CATEGORY);
                mViewPager.setCurrentItem(Constants.CATEGORY);
                break;
            case R.id.btn_fav:
                changeTabState(Constants.FAVOURITE);
                mViewPager.setCurrentItem(Constants.FAVOURITE);
                break;
            case R.id.btn_cart:
                changeTabState(Constants.CART);
                mViewPager.setCurrentItem(Constants.CART);
                break;
            case R.id.btn_profile:
                changeTabState(Constants.PROFILE);
                mViewPager.setCurrentItem(Constants.PROFILE);
                break;
        }
    }

    private void changeTabState(int state) {
        switch (state) {
            case Constants.HOME:
                btn_home.setImageResource(R.drawable.home_selected);
                btn_category.setImageResource(R.drawable.category_unselected);
                btn_fav.setImageResource(R.drawable.fav_unselected);
                btn_cart.setImageResource(R.drawable.cart_unselected);
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
                    btn_profile.setImageResource(R.drawable.shop_unselected);
                } else {
                    btn_profile.setImageResource(R.drawable.profile_unselected);
                }
                break;
            case Constants.CATEGORY:
                btn_home.setImageResource(R.drawable.home_unselected);
                btn_category.setImageResource(R.drawable.category_unselected);
                btn_fav.setImageResource(R.drawable.fav_unselected);
                btn_cart.setImageResource(R.drawable.cart_unselected);
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
                    btn_profile.setImageResource(R.drawable.shop_unselected);
                } else {
                    btn_profile.setImageResource(R.drawable.profile_unselected);
                }
                break;
            case Constants.FAVOURITE:
                btn_home.setImageResource(R.drawable.home_unselected);
                btn_category.setImageResource(R.drawable.category_unselected);
                btn_fav.setImageResource(R.drawable.fav_selected);
                btn_cart.setImageResource(R.drawable.cart_unselected);
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
                    btn_profile.setImageResource(R.drawable.shop_unselected);
                } else {
                    btn_profile.setImageResource(R.drawable.profile_unselected);
                }
                break;
            case Constants.CART:
                btn_home.setImageResource(R.drawable.home_unselected);
                btn_category.setImageResource(R.drawable.category_unselected);
                btn_fav.setImageResource(R.drawable.fav_unselected);
                btn_cart.setImageResource(R.drawable.cart_selected);
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
                    btn_profile.setImageResource(R.drawable.shop_unselected);
                } else {
                    btn_profile.setImageResource(R.drawable.profile_unselected);
                }
                break;
            case Constants.PROFILE:
                btn_home.setImageResource(R.drawable.home_unselected);
                btn_category.setImageResource(R.drawable.category_unselected);
                btn_fav.setImageResource(R.drawable.fav_unselected);
                btn_cart.setImageResource(R.drawable.cart_unselected);
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
                    btn_profile.setImageResource(R.drawable.shop_selected);
                } else {
                    btn_profile.setImageResource(R.drawable.profile_selected);
                }
                break;
        }
    }
}
