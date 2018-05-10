package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtmweb.etrenaapp.LoginActivity;
import com.dtmweb.etrenaapp.MainActivity;
import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.MainPagerAdapter;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.models.ProductObject;
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
    public FragmentManager mFragManager;
    private FragmentTransaction fragTransaction = null;
    private MainActivity activity = null;
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
        activity = (MainActivity) getActivity();
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
                activity.currentTabItemSelected = Constants.HOME;
                changeTabState(Constants.HOME);
                mViewPager.setCurrentItem(Constants.HOME);
                dismissAllFragmentStack();
                activity.changeHeaderLayout(Constants.FRAG_HOME);
                break;
            case R.id.btn_category:
                activity.currentTabItemSelected = Constants.CATEGORY;
                changeTabState(Constants.CATEGORY);
                mViewPager.setCurrentItem(Constants.CATEGORY);
                dismissAllFragmentStack();
                activity.changeHeaderLayout(Constants.FRAG_HOME);
                break;
            case R.id.btn_fav:
                activity.currentTabItemSelected = Constants.FAVOURITE;
                changeTabState(Constants.FAVOURITE);
                mViewPager.setCurrentItem(Constants.FAVOURITE);
                dismissAllFragmentStack();
                activity.changeHeaderLayout(Constants.FRAG_HOME);
                break;
            case R.id.btn_cart:
                activity.currentTabItemSelected = Constants.CART;
                changeTabState(Constants.CART);
                mViewPager.setCurrentItem(Constants.CART);
                dismissAllFragmentStack();
                activity.changeHeaderLayout(Constants.FRAG_HOME);
                break;
            case R.id.btn_profile:
                if(!GlobalUtils.user_type.equals(Constants.CATEGORY_NON_LOGGED)) {
                    activity.currentTabItemSelected = Constants.PROFILE;
                    changeTabState(Constants.PROFILE);
                    mViewPager.setCurrentItem(Constants.PROFILE);
                    dismissAllFragmentStack();
                    activity.changeHeaderLayout(Constants.FRAG_HOME);
                }else{
                    //Show Login Screens
                    startActivity(new Intent(activity, LoginActivity.class));
                    activity.overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_scale_to_center);
                }
                break;
        }
    }

    public void dismissAllFragmentStack() {
        if (mFragManager != null) {
            for (int i = 0; i < mFragManager.getBackStackEntryCount(); ++i) {
                mFragManager.popBackStack();
            }
        }
    }

    public void changeTabState(int state) {
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
                btn_category.setImageResource(R.drawable.category_selected);
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
            case Constants.DISSELECT_ALL:
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
        }
    }


    public void addSecondStageFragment(int destFragId, Object obj) {
        mFragManager = getChildFragmentManager();
        // create transaction
        fragTransaction = mFragManager.beginTransaction();
        fragTransaction.setCustomAnimations(R.anim.view_transition_in_left,
                R.anim.view_transition_out_right, R.anim.view_transition_in_left,
                R.anim.view_transition_out_right);

        Bundle args = null;
        if (obj != null) {
            if (obj.getClass().toString().equals(ProductObject.class.toString())) {
                args = new Bundle();
                args.putParcelable(obj.getClass().toString(), (ProductObject) obj);
            }

            if (obj.getClass().toString().equals(String.class.toString())) {
                args = new Bundle();
                args.putString(obj.getClass().toString(), (String) obj);
            }
        }


        Fragment frag = null;
        switch (destFragId) {
            case Constants.FRAG_ADD_PRODUCT:
                frag = new AddProductFragment();
                activity.changeHeaderLayout(Constants.FRAG_ADD_PRODUCT);
                activity.setUpHeaderRightButton(2);
                activity.LockRightDrawer();
                break;
            case Constants.FRAG_PRODUCT_DETAILS:
                frag = new ProductDetailsFragment();
                activity.setUpHeaderRightButton(1);
                activity.LockRightDrawer();
                break;
            case Constants.FRAG_MANAGE_PRODUCTS:
                frag = new ManageProductFragment();
                activity.changeHeaderLayout(Constants.FRAG_MANAGE_PRODUCTS);
                break;
            case Constants.FRAG_MANAGE_ORDERS:
                frag = new ManageOrderFragment();
                break;
            case Constants.FRAG_MY_STORE:
                frag = new MyStoreFragment();
                break;
            case Constants.FRAG_ADD_NEW_CARD:
                frag = new AddCardFragment();
                break;
            case Constants.FRAG_CHOOSE_PAYMENT_METHOD:
                frag = new ChoosePaymentTypeFragment();
                break;
            case Constants.FRAG_EDIT_PROFILE:
                frag = new EditProfileFragment();
                break;
            case Constants.FRAG_BROWSE_PRODUCT:
                frag = new BrowseProductFragment();
                break;
            case Constants.FRAG_BILLING_ADDRESS:
                frag = new AddressFragment();
                break;
            default:
                break;
        }
        // add argument for sent to other fragment
        if (args != null) {
            frag.setArguments(args);
        }

        activity.mCurrentFrag = frag;

        // param 1: container id, param 2: new fragment, param 3: fragment id
        fragTransaction.add(R.id.content_rl_container, frag, String.valueOf(destFragId));
        fragTransaction.addToBackStack(String.valueOf(destFragId));
        fragTransaction.commit();
    }
}
