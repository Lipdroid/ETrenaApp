package com.dtmweb.etrendapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.constants.UrlConstants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.SellerObject;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private EditText et_mail = null;
    private EditText et_password = null;
    private Button btn_go = null;
    private TextView btn_forget_pass = null;
    private TextView btn_sign_up = null;
    private Context mContext = null;
    private String password = null;
    private String email = null;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        findViews();
        initListenersForViews();
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        afterClickBack();
    }

    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_scale_to_center, R.anim.anim_slide_out_bottom);
    }

    private void initListenersForViews() {
        btn_go.setOnClickListener(this);
        btn_forget_pass.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);

    }

    private void findViews() {
        et_mail = (EditText) findViewById(R.id.et_mail);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_go = (Button) findViewById(R.id.btn_go);
        btn_forget_pass = (TextView) findViewById(R.id.btn_forget_pass);
        btn_sign_up = (TextView) findViewById(R.id.btn_sign_up);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                afterClickSumbit();
                break;
            case R.id.btn_forget_pass:
                afterClickForgetPassword();
                break;
            case R.id.btn_sign_up:
                afterClickSignUp();
                break;
        }
    }

    private void afterClickSignUp() {
        startActivity(new Intent(mContext, UserCategoryActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    private void afterClickForgetPassword() {
    }

    private void afterClickSumbit() {
        //goToMainPage();
        checkFieldValidation();
    }

    private void goToMainPage() {

        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }


    private void checkFieldValidation() {
        email = et_mail.getText().toString();
        password = et_password.getText().toString();

        if (email == null || email.equals("")) {
            Log.e(TAG, "password input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "password input is empty", "OK", null);
            return;
        } else if (password == null || password.equals("")) {
            Log.e(TAG, "contact no input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "contact no input is empty", "OK", null);
            return;
        }

        requestToLogin();

    }

    private void requestToLogin() {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_EMAIL, email);
        params.put(Constants.PARAM_PASSWORD, password);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_LOGIN, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                GlobalUtils.dismissLoadingProgress();
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("success") && jsonObject.getString("success").equals("true")) {
                            SellerObject mUserObj = new SellerObject();
                            if (jsonObject.has("seller")) {
                                String pro_img = jsonObject.getString("seller");
                                mUserObj.setPro_img(UrlConstants.BASE_URL + pro_img);
                                GlobalUtils.saveCurrentUserSeller(mUserObj);
                            }

                            if (jsonObject.has("Token")) {
                                String token = jsonObject.getString("Token");
                                //save the token in preference for furthur api call
                                SharedPreferencesUtils.putString(mContext, Constants.PREF_TOKEN, token);
                            }

                            //call profile api to get the seller details
                            requestToGetUserDetails();
                        } else if (jsonObject.has("success") && jsonObject.get("success").equals("false")) {
                            String error = jsonObject.getString("error_message");
                            GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }
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

    private void requestToGetUserDetails(){
        HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_SELLER_PROFILE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                GlobalUtils.dismissLoadingProgress();
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("success") && jsonObject.getString("success").equals("true")) {
                            SellerObject mUserObj = new SellerObject();
                            if (jsonObject.has("seller")) {
                                String userId = jsonObject.getString("id");
                                String userName = jsonObject.getString("username");
                                String email = jsonObject.getString("email");
                                mUserObj.setUserId(userId);
                                mUserObj.setUsername(userName);
                                mUserObj.setEmail(email);
                                mUserObj.setUser_type(Constants.CATEGORY_SELLER);

                                if(jsonObject.has("seller")){
                                    JSONArray jsonArray = jsonObject.getJSONArray("seller");
                                    JSONObject jsonSelller = jsonArray.getJSONObject(0);
                                    String pro_img = jsonSelller.getString("seller");
                                    String store_name = jsonSelller.getString("store_name");
                                    String bank_name = jsonSelller.getString("bank_name");
                                    String bank_acc_name = jsonSelller.getString("bank_acc_name");
                                    String bank_acc_number = jsonSelller.getString("bank_acc_number");
                                    String country = jsonSelller.getString("country");
                                    String city = jsonSelller.getString("city");
                                    String address = jsonSelller.getString("address");
                                    String store_owner = jsonSelller.getString("store_owner");
                                    String contact_no = jsonSelller.getString("contact_no");
                                    String instagram = jsonSelller.getString("instagram");

                                    mUserObj.setPro_img(pro_img);
                                    mUserObj.setStore_name(store_name);
                                    mUserObj.setBank_name(bank_name);
                                    mUserObj.setBank_acc_name(bank_acc_name);
                                    mUserObj.setBank_acc_number(bank_acc_number);
                                    mUserObj.setCountry(country);
                                    mUserObj.setCity(city);
                                    mUserObj.setAddress(address);
                                    mUserObj.setStore_owner(store_owner);
                                    mUserObj.setContact_no(contact_no);
                                    mUserObj.setInstagram(instagram);

                                }

                                GlobalUtils.saveCurrentUserSeller(mUserObj);
                            }
                            afterClickBack();

                        } else if (jsonObject.has("success") && jsonObject.get("success").equals("false")) {
                            String error = jsonObject.getString("error_message");
                            GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }
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
}
