package com.dtmweb.etrendapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.UserObject;
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
    private UserObject mUserObj = null;

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
        finish();
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

        final HashMap<String, Object> params = new HashMap<String, Object>();
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
                        if (jsonObject.has("token")) {
                            String token = jsonObject.getString("token");
                            //save the token in preference for furthur api call
                            SharedPreferencesUtils.putString(mContext, Constants.PREF_TOKEN, token);
                            //Parse the User data
                            if (jsonObject.has("user")) {
                                mUserObj = new UserObject();
                                JSONObject userJson = jsonObject.getJSONObject("user");

                                if (userJson.has("id")) {
                                    mUserObj.setUserId(userJson.getString("id"));
                                }
                                if (userJson.has("name")) {
                                    mUserObj.setUsername(userJson.getString("name"));
                                }
                                if (userJson.has("email")) {
                                    mUserObj.setEmail(userJson.getString("email"));
                                }
                                if (userJson.has("phone")) {
                                    mUserObj.setContact_no(userJson.getString("phone"));
                                }
                                if (userJson.has("image")) {
                                    mUserObj.setPro_img(userJson.getString("image"));
                                }

                                if (userJson.has("city")) {
                                    JSONObject jsonCity = userJson.getJSONObject("city");
                                    if (jsonCity.has("name")) {
                                        mUserObj.setCity(jsonCity.getString("name"));
                                    }
                                    if (jsonCity.has("country")) {
                                        JSONObject jsonCountry = jsonCity.getJSONObject("country");
                                        if (jsonCountry.has("name")) {
                                            mUserObj.setCountry(jsonCountry.getString("name"));
                                        }

                                    }
                                }

                                if (userJson.has("instagram")) {
                                    mUserObj.setInstagram(userJson.getString("instagram"));
                                }
                                if (userJson.has("address")) {
                                    mUserObj.setAddress(userJson.getString("address"));
                                }

                                if (userJson.has("is_subscribed")) {
                                    if (!userJson.isNull("is_subscribed"))
                                        mUserObj.setIs_subscribed(userJson.getBoolean("is_subscribed"));
                                    else {
                                        //store is not created yet
                                    }
                                }

                                //set the type
                                Boolean is_seller = false;
                                Boolean is_buyer = false;
                                if (userJson.has("is_buyer")) {
                                    is_buyer = userJson.getBoolean("is_buyer");
                                }
                                if (userJson.has("is_seller")) {
                                    is_seller = userJson.getBoolean("is_seller");
                                }


                                mUserObj.setUser_type(is_buyer, is_seller);

                            }
                            //save the current user
                            GlobalUtils.saveCurrentUser(mUserObj);
                            //logged in
                            GlobalUtils.isLoggedIn = true;
                            //save the user type
                            GlobalUtils.user_type = mUserObj.getUser_type();

                            afterClickBack();

                        } else {
                            //parse errors
                            GlobalUtils.parseErrors(mContext, params, jsonObject);

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
