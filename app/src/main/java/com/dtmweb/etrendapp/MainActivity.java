package com.dtmweb.etrendapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.fragments.BaseFragment;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private Toolbar mToolBar = null;
    private DrawerLayout mDrawerLayout = null;
    private DrawerLayout.DrawerListener mDrawerListener = null;
    private Context mContext = null;
    private ImageView btn_left_drawer = null;
    private ImageView btn_right_drawer = null;
    private RelativeLayout drawer_left_layout = null;
    private static LinearLayout drawer_right_layout = null;
    private boolean isOutSideClicked = false;

    private RelativeLayout main_container = null;
    //left drawers items
    private LinearLayout btn_home = null;
    private LinearLayout btn_my_chart = null;
    private LinearLayout btn_my_orders = null;
    private LinearLayout btn_manage_products = null;
    private LinearLayout btn_manage_orders = null;
    private LinearLayout btn_my_store = null;
    private LinearLayout btn_language = null;
    private LinearLayout btn_my_plan = null;
    private LinearLayout btn_logout = null;
    private LinearLayout btn_terms = null;
    private LinearLayout btn_rate = null;
    private LinearLayout btn_about = null;
    private ImageView btn_back_left = null;
    private ImageView btn_edit_profile = null;
    private ImageView arrow_language = null;
    private LinearLayout language_layout = null;
    private LinearLayout btn_arabic = null;
    private LinearLayout btn_english = null;
    private RelativeLayout user_info = null;

    //right drawer items
    private EditText et_search = null;
    private EditText et_price = null;
    private EditText et_location = null;
    private EditText et_store = null;
    private ImageView btn_back_right = null;
    private ImageView btn_right_back = null;
    private ImageView btn_right_cross = null;
    private Button btn_search = null;


    //fragment variables
    private FragmentManager mFragManager;
    public Fragment mCurrentFrag;
    private TextView header_title;
    private ImageView header_image;
    private FragmentTransaction fragTransaction = null;
    private ArrayList<Fragment> mSecondStageFragArray = null;
    private PopupWindow popupWindow = null;
    private boolean isLanguageVisible = false;
    public BaseFragment mBaseFrag;
    public int currentTabItemSelected = Constants.HOME;


    public boolean gotExtraData = false;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mSecondStageFragArray = new ArrayList<Fragment>();
        findViews();
        setUpLeftDrawer(GlobalUtils.user_type);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        setListenersForViews();
        //show the initial home page
        afterClickMenuItem(Constants.FRAG_HOME);
        try {
            String extra = getIntent().getExtras().getString(Constants.EXTRA_FROM_CHOOSE_PLAN);
            if (extra != null) {
                gotExtraData = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }


    private void setListenersForViews() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                clearFocus();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        btn_left_drawer.setOnClickListener(this);
        btn_right_drawer.setOnClickListener(this);
        //left drawer listener
        btn_home.setOnClickListener(this);
        btn_manage_products.setOnClickListener(this);
        btn_manage_orders.setOnClickListener(this);
        btn_my_store.setOnClickListener(this);
        btn_language.setOnClickListener(this);
        btn_my_plan.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_terms.setOnClickListener(this);
        btn_rate.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_back_left.setOnClickListener(this);
        btn_edit_profile.setOnClickListener(this);
        btn_my_chart.setOnClickListener(this);
        btn_my_orders.setOnClickListener(this);
        btn_arabic.setOnClickListener(this);
        btn_english.setOnClickListener(this);
        //right drawer listener
        btn_back_right.setOnClickListener(this);
        btn_right_back.setOnClickListener(this);
        btn_right_cross.setOnClickListener(this);

        btn_search.setOnClickListener(this);

    }

    private void findViews() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //appbar items
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        drawer_left_layout = (RelativeLayout) findViewById(R.id.drawer_left);
        drawer_right_layout = (LinearLayout) findViewById(R.id.drawer_right);
        header_title = (TextView) findViewById(R.id.header_title);
        header_image = (ImageView) findViewById(R.id.header_image);
        btn_right_back = (ImageView) findViewById(R.id.btn_right_back);
        btn_right_cross = (ImageView) findViewById(R.id.btn_right_cross);
        btn_left_drawer = (ImageView) findViewById(R.id.btn_left_drawer);
        btn_right_drawer = (ImageView) findViewById(R.id.btn_right_drawer);

        //left drawer items
        btn_home = (LinearLayout) findViewById(R.id.btn_home);
        btn_my_chart = (LinearLayout) findViewById(R.id.btn_my_chart);
        btn_my_orders = (LinearLayout) findViewById(R.id.btn_my_orders);
        btn_manage_products = (LinearLayout) findViewById(R.id.btn_manage_products);
        btn_manage_orders = (LinearLayout) findViewById(R.id.btn_manage_orders);
        btn_my_store = (LinearLayout) findViewById(R.id.btn_my_store);
        btn_language = (LinearLayout) findViewById(R.id.btn_language);
        btn_my_plan = (LinearLayout) findViewById(R.id.btn_my_plan);
        btn_logout = (LinearLayout) findViewById(R.id.btn_logout);
        btn_terms = (LinearLayout) findViewById(R.id.btn_terms);
        btn_rate = (LinearLayout) findViewById(R.id.btn_rate);
        btn_about = (LinearLayout) findViewById(R.id.btn_about);
        btn_back_left = (ImageView) findViewById(R.id.btn_back_left);
        btn_edit_profile = (ImageView) findViewById(R.id.btn_edit_profile);
        arrow_language = (ImageView) findViewById(R.id.arrow_language);
        language_layout = (LinearLayout) findViewById(R.id.language_layout);
        btn_arabic = (LinearLayout) findViewById(R.id.btn_arabic);
        btn_english = (LinearLayout) findViewById(R.id.btn_english);
        user_info = (RelativeLayout) findViewById(R.id.user_info);

        //right drawer item
        et_search = (EditText) findViewById(R.id.et_search);
        et_price = (EditText) findViewById(R.id.et_price);
        et_location = (EditText) findViewById(R.id.et_location);
        et_store = (EditText) findViewById(R.id.et_store);
        btn_back_right = (ImageView) findViewById(R.id.btn_back_right);
        btn_search = (Button) findViewById(R.id.btn_search);

        //main layout
        main_container = (RelativeLayout) findViewById(R.id.main_container);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left_drawer:
                afterClickLeftDrawerBtn();
                break;
            case R.id.btn_right_drawer:
                afterClickRightDrawerBtn();
                break;
            case R.id.btn_home:
                closeLeftDrawer();
                afterClickMenuItem(Constants.FRAG_HOME);
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case R.id.btn_my_chart:
                closeLeftDrawer();
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case R.id.btn_my_orders:
                closeLeftDrawer();
                break;
            case R.id.btn_manage_products:
                /**ACTIVE*/
                mBaseFrag.changeTabState(Constants.DISSELECT_ALL);
                closeLeftDrawer();
                afterClickMenuItem(Constants.FRAG_MANAGE_PRODUCTS);
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case R.id.btn_manage_orders:
                /**ACTIVE*/
                mBaseFrag.changeTabState(Constants.DISSELECT_ALL);
                closeLeftDrawer();
                afterClickMenuItem(Constants.FRAG_MANAGE_ORDERS);
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case R.id.btn_my_store:
                closeLeftDrawer();
                afterClickMenuItem(Constants.FRAG_MY_STORE);
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case R.id.btn_language:
                /**ACTIVE*/
                //showPopup(arrow_language);
                if (!isLanguageVisible) {
                    language_layout.setVisibility(View.VISIBLE);
                    rotateView(arrow_language, 0, -180);
                } else {
                    language_layout.setVisibility(View.GONE);
                    rotateView(arrow_language, -180, 0);
                }
                isLanguageVisible = !isLanguageVisible;
                break;
            case R.id.btn_my_plan:
                /**ACTIVE*/
                mBaseFrag.changeTabState(Constants.DISSELECT_ALL);
                afterClickMenuItem(Constants.FRAG_MY_PLAN);
                closeLeftDrawer();
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case R.id.btn_logout:
                /**ACTIVE*/
                closeLeftDrawer();
                callLogoutAPI();
                break;
            case R.id.btn_terms:
                /**ACTIVE*/
                closeLeftDrawer();
                break;
            case R.id.btn_rate:
                /**ACTIVE*/
                closeLeftDrawer();
                break;
            case R.id.btn_about:
                /**ACTIVE*/
                closeLeftDrawer();
                break;
            case R.id.btn_back_left:
                closeLeftDrawer();
                break;
            case R.id.btn_edit_profile:
                /**ACTIVE*/
                closeLeftDrawer();
                afterClickMenuItem(Constants.FRAG_EDIT_PROFILE);
                LockRightDrawer();
                setUpHeaderRightButton(3);
                break;
            case R.id.btn_back_right:
                closeRightDrawer();
                break;
            case R.id.btn_search:
                closeRightDrawer();
            case R.id.btn_right_back:
                afterClickBack();
                break;
            case R.id.btn_right_cross:
                afterClickBack();
            case R.id.btn_arabic:
                /**ACTIVE*/
                closeLeftDrawer();
                //change Language to arabic
            case R.id.btn_english:
                /**ACTIVE*/
                closeLeftDrawer();
                //change Language to english
                break;
        }
    }

    private void afterClickBack() {
        FragmentManager fragmentManager = mBaseFrag.mFragManager;
        if (fragmentManager != null) {
            int count = fragmentManager.getBackStackEntryCount();
            if (count == 0) {
                showExitDialog();
            } else {
                String title = fragmentManager.getBackStackEntryAt(count - 1).getName();
                fragmentManager.popBackStack();
                //super.onBackPressed();
                updateActionBar(Integer.valueOf(title), null);
            }
            //again check its 0 or not
            count = fragmentManager.getBackStackEntryCount();
            if (count == 1) {
                mBaseFrag.changeTabState(currentTabItemSelected);
                changeHeaderLayout(Constants.FRAG_HOME);
            }
        } else {
            showExitDialog();
        }


    }

    private void openLeftDrawer() {
        mDrawerLayout.openDrawer(drawer_left_layout);
        //focus on the left layout
        drawer_left_layout.bringToFront();
        drawer_left_layout.requestLayout();
    }

    private void closeLeftDrawer() {
        mDrawerLayout.closeDrawer(drawer_left_layout);
        //focus on the center layout
        //main_container.bringToFront();
        //main_container.requestLayout();
    }

    private void clearFocus() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_price.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_store.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_location.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);

    }

    private void openRightDrawer() {
        mDrawerLayout.openDrawer(drawer_right_layout);
        //focus on the right layout
        drawer_right_layout.bringToFront();
        drawer_right_layout.requestLayout();
    }

    private void closeRightDrawer() {
        mDrawerLayout.closeDrawer(drawer_right_layout);
        //focus on the center layout
        //main_container.bringToFront();
        //main_container.requestLayout();
    }

    private void afterClickRightDrawerBtn() {
        if (mDrawerLayout.isDrawerOpen(drawer_right_layout)) {
            closeRightDrawer();
        } else if (!mDrawerLayout.isDrawerOpen(drawer_right_layout)) {
            openRightDrawer();
        }
    }

    private void afterClickLeftDrawerBtn() {
        if (mDrawerLayout.isDrawerOpen(drawer_left_layout)) {
            closeLeftDrawer();
        } else if (!mDrawerLayout.isDrawerOpen(drawer_left_layout)) {
            openLeftDrawer();
        }
    }

    public void addFragment(int fragId, boolean isHasAnimation) {
        // init fragment manager
        mFragManager = getSupportFragmentManager();
        // create transaction
        fragTransaction = mFragManager.beginTransaction();

        // init argument
        Bundle args = new Bundle();

        //check if there is any backstack if yes then remove it
        int count = mFragManager.getBackStackEntryCount();
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            mFragManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }


        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag.getTag() != null && mCurrentFrag.getTag().equals(String.valueOf(fragId))) {
            return;
        }

        Fragment newFrag = null;
        // identify which fragment will be called
        switch (fragId) {
            case Constants.FRAG_HOME:
                newFrag = new BaseFragment();
                break;
            default:
                break;
        }

        mBaseFrag = (BaseFragment) newFrag;

        // set animation
        if (isHasAnimation) {
            fragTransaction.setCustomAnimations(
                    R.anim.view_transition_in_left,
                    R.anim.view_transition_out_right,
                    R.anim.view_transition_in_left,
                    R.anim.view_transition_out_right);
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id
        fragTransaction.replace(R.id.main_container, newFrag, String.valueOf(fragId));
        // prevent showed when user press back fabReview
        //fragTransaction.addToBackStack(String.valueOf(fragId));
        fragTransaction.commit();
    }

    public void changeHeaderLayout(int fragId) {
        switch (fragId) {
            case Constants.FRAG_HOME:
                header_title.setText("");
                header_image.setVisibility(View.VISIBLE);
                setUpHeaderRightButton(0);
                break;
            case Constants.FRAG_MANAGE_PRODUCTS:
                header_title.setText("");
                header_image.setVisibility(View.INVISIBLE);
                break;
            case Constants.FRAG_MANAGE_ORDERS:
                header_title.setText("");
                header_image.setVisibility(View.VISIBLE);
                break;
            case Constants.FRAG_MY_STORE:
                header_title.setText("");
                header_image.setVisibility(View.VISIBLE);
                break;
            case Constants.FRAG_ADD_PRODUCT:
                header_title.setText("Add Product");
                header_image.setVisibility(View.GONE);
                break;
            case Constants.FRAG_EDIT_PROFILE:
                header_title.setText("Edit Profile");
                header_image.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void afterClickMenuItem(int fragId) {
        if (fragId == Constants.FRAG_HOME) {
            // add fragment by id
            addFragment(fragId, false);
            // change header layout
            changeHeaderLayout(fragId);
        } else {
            mBaseFrag.addSecondStageFragment(fragId, null);
        }
    }


    public void setUpHeaderRightButton(int type) {
        switch (type) {
            case 0:
                btn_right_drawer.setVisibility(View.VISIBLE);
                btn_right_back.setVisibility(View.GONE);
                btn_right_cross.setVisibility(View.GONE);
                break;
            case 1:
                btn_right_drawer.setVisibility(View.GONE);
                btn_right_back.setVisibility(View.VISIBLE);
                btn_right_cross.setVisibility(View.GONE);
                break;
            case 2:
                btn_right_drawer.setVisibility(View.GONE);
                btn_right_back.setVisibility(View.GONE);
                btn_right_cross.setVisibility(View.VISIBLE);
                break;
            case 3:
                btn_right_drawer.setVisibility(View.GONE);
                btn_right_back.setVisibility(View.GONE);
                btn_right_cross.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        afterClickBack();
    }

    public void updateActionBar(int destFragId, Object obj) {
        String title = "";
        switch (destFragId) {
            case Constants.FRAG_ADD_PRODUCT:
                title = "";
                mCurrentFrag = null;
                UnlockRightDrawer();
                setUpHeaderRightButton(0);
                break;
            case Constants.FRAG_PRODUCT_DETAILS:
                title = "";
                mCurrentFrag = null;
                setUpHeaderRightButton(0);
                UnlockRightDrawer();
                break;
        }

        header_title.setText(title);
    }

    private void setUpLeftDrawer(String userType) {
        switch (userType) {
            case Constants.CATEGORY_BUYER:
                btn_my_chart.setVisibility(View.GONE);
                btn_my_orders.setVisibility(View.GONE);
                btn_manage_products.setVisibility(View.GONE);
                btn_manage_orders.setVisibility(View.VISIBLE);
                btn_my_store.setVisibility(View.GONE);
                btn_my_plan.setVisibility(View.GONE);
                user_info.setVisibility(View.VISIBLE);
                break;
            case Constants.CATEGORY_SELLER:
                btn_my_chart.setVisibility(View.GONE);
                btn_my_orders.setVisibility(View.GONE);
                btn_manage_products.setVisibility(View.VISIBLE);
                btn_manage_orders.setVisibility(View.VISIBLE);
                btn_my_store.setVisibility(View.GONE);
                btn_my_plan.setVisibility(View.VISIBLE);
                user_info.setVisibility(View.VISIBLE);
                break;
            case Constants.CATEGORY_NON_LOGGED:
                btn_my_chart.setVisibility(View.GONE);
                btn_my_orders.setVisibility(View.GONE);
                btn_manage_products.setVisibility(View.GONE);
                btn_manage_orders.setVisibility(View.GONE);
                btn_my_store.setVisibility(View.GONE);
                btn_my_plan.setVisibility(View.GONE);
                btn_logout.setVisibility(View.GONE);
                user_info.setVisibility(View.GONE);
                break;
        }
    }


    public void LockRightDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void UnlockRightDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void rotateView(View view, int fromAngle, int toAngle) {
        RotateAnimation rotate = new RotateAnimation(fromAngle, toAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new LinearInterpolator());
        view.startAnimation(rotate);
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void addFrag(int frag, Object object) {
        mBaseFrag.addSecondStageFragment(frag, object);
    }

    public void callLogoutAPI(){

    }
}