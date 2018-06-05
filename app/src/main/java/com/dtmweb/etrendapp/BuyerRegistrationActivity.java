package com.dtmweb.etrendapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.ImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class BuyerRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private ImageView btn_cross = null;
    private ImageView btn_image_selection = null;
    private Button btn_go = null;
    private EditText et_full_name = null;
    private EditText et_password = null;
    private EditText et_password_retype = null;
    private EditText et_user_name = null;
    private EditText et_mail = null;
    private EditText et_country = null;
    private EditText et_city = null;
    private EditText et_address = null;
    private EditText et_contact = null;
    private Context mContext = null;

    String fullname = null;
    String userName = null;
    String password = null;
    String password_retype = null;
    String country = null;
    String city = null;
    String address = null;
    String email = null;
    String contact = null;

    private static final String TAG = "BuyerRegistrationActivity";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        afterClickBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_registration);
        mContext = this;
        findViews();
        initListenersForViews();
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void findViews() {
        btn_cross = (ImageView) findViewById(R.id.btn_cross);
        btn_image_selection = (ImageView) findViewById(R.id.btn_image_selection);
        btn_go = (Button) findViewById(R.id.btn_go);
        et_full_name = (EditText) findViewById(R.id.et_full_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_retype = (EditText) findViewById(R.id.et_password_retype);
        et_country = (EditText) findViewById(R.id.et_country);
        et_city = (EditText) findViewById(R.id.et_city);
        et_address = (EditText) findViewById(R.id.et_address);
        et_contact = (EditText) findViewById(R.id.et_contact);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_mail = (EditText) findViewById(R.id.et_mail);
    }

    private void initListenersForViews() {
        btn_go.setOnClickListener(this);
        btn_cross.setOnClickListener(this);
        btn_image_selection.setOnClickListener(this);
        et_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) afterClickCountry();
                return false;
            }
        });
        et_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) afterClickCity();
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                afterClickSumbit();
                break;
            case R.id.btn_cross:
                afterClickBack();
                break;
            case R.id.btn_image_selection:
                afterClickImageSelection();
                break;
            case R.id.et_country:
                afterClickCountry();
                break;
            case R.id.et_city:
                afterClickCity();
                break;

        }
    }

    private void afterClickCity() {
        moveToChoosenActivity(Constants.TYPE_CITY);
    }

    private void afterClickCountry() {
        moveToChoosenActivity(Constants.TYPE_COUNTRY);
    }

    private void afterClickImageSelection() {
    }

    private void afterClickIntaConnect() {
    }

    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    private void afterClickSumbit() {
        //go to main after checking validity and api call
        checkFieldValidation();

    }

    private void goToMainPage() {
        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @SuppressLint("LongLogTag")
    private void checkFieldValidation() {
        fullname = et_full_name.getText().toString();
        userName = et_user_name.getText().toString();
        password = et_password.getText().toString();
        password_retype = et_password_retype.getText().toString();
        country = et_country.getText().toString();
        city = et_city.getText().toString();
        address = et_address.getText().toString();
        email = et_mail.getText().toString();
        contact = et_contact.getText().toString();

        if (fullname == null || fullname.equals("")) {
            Log.e(TAG, "user name input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "full name input is empty", "OK", null);
            return;
        } else if (userName == null || userName.equals("")) {
            Log.e(TAG, "password input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "username input is empty", "OK", null);
            return;
        } else if (password == null || password.equals("")) {
            Log.e(TAG, "password input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "password input is empty", "OK", null);
            return;
        } else if (password.length() < 8 && !GlobalUtils.isValidPassword(password)) {
            Log.e(TAG, "password is not Valid(Minimum 8 digit)");
            GlobalUtils.showInfoDialog(mContext, "Error", "password is not Valid(Minimum 8 digit)", "OK", null);
            return;
        } else if (password_retype == null || password_retype.equals("")) {
            Log.e(TAG, "password retype input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "password retype input is empty", "OK", null);
            return;
        } else if (country == null || country.equals("")) {
            Log.e(TAG, "country input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "country name input is empty", "OK", null);
            return;
        } else if (email == null || email.equals("")) {
            Log.e(TAG, "email input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "email input is empty", "OK", null);
            return;
        } else if (!password.equals(password_retype)) {
            Log.e(TAG, "confirm password do not match");
            GlobalUtils.showInfoDialog(mContext, "Error", "confirm password do not match", "OK", null);
            return;
        } else if (contact == null || contact.equals("")) {
            Log.e(TAG, "contact no input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "contact no input is empty", "OK", null);
            return;
        }

        requestToSighUp();

    }

    private void requestToSighUp() {
        JSONObject jsonParams = new JSONObject();

        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.PARAM_EMAIL, email);
        params.put(Constants.PARAM_PASSWORD, password);
        params.put(Constants.PARAM_USERNAME, userName);
        params.put(Constants.PARAM_COUNTRY, country);
        params.put(Constants.PARAM_CITY, city);
        params.put(Constants.PARAM_ADDRESS, address);
        params.put(Constants.PARAM_FULL_NAME, fullname);
        params.put(Constants.PARAM_CONTACT_NO, contact);


        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_REGISTER_USER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                GlobalUtils.dismissLoadingProgress();


            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();

            }

            @Override
            public void onException(Exception e) {

                GlobalUtils.dismissLoadingProgress();

            }
        });

        mRequestAsync.execute();

    }

    private void moveToChoosenActivity(int type) {

        Intent intent = new Intent(this, ChoosenActivity.class);
        switch (type) {
            case Constants.TYPE_COUNTRY:
                intent.putExtra("extra", Constants.TYPE_COUNTRY);
                break;
            case Constants.TYPE_CITY:
                intent.putExtra("extra", Constants.TYPE_COUNTRY);
                break;
        }

        intent.putExtra(Integer.class.toString(), type);
        startActivityForResult(intent, type);
        overridePendingTransition(R.anim.anim_slide_in_bottom,
                R.anim.anim_slide_out_bottom);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == Constants.TYPE_CITY) {
            Bundle bundle = data.getExtras();
            //WHAT TO DO TO GET THE BUNDLE VALUES//
            String city = bundle.getString("city");
            et_city.setText(city);
        }

        if (requestCode == Constants.TYPE_COUNTRY) {
            Bundle bundle = data.getExtras();
            //WHAT TO DO TO GET THE BUNDLE VALUES//
            String country = bundle.getString("country");
            et_country.setText(country);
        }
    }

}
