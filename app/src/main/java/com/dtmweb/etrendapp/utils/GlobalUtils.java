package com.dtmweb.etrendapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dtmweb.etrendapp.EtrendApplication;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.customViews.CustomDialog;
import com.dtmweb.etrendapp.customViews.CustomProgressDialog;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.models.UserObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mdmunirhossain on 5/4/18.
 */

public class GlobalUtils {
    public static String user_type = Constants.CATEGORY_NON_LOGGED;
    public static Boolean addAditionalHeader = false;
    public static String additionalHeaderTag = null;
    public static String additionalHeaderValue = null;
    private static CustomProgressDialog sPdLoading = null;
    private static StoreObject mStoreObject = null;
    private static UserObject mUserObj = null;
    public static Boolean isLoggedIn = false;
    public static Boolean isAppFirstOpen = false;

    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) EtrendApplication.AudienceApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void showInfoDialog(Context context, String title, String body, String action, final DialogCallback dialogCallback) {
        final CustomDialog infoDialog = new CustomDialog(context, R.style.CustomDialogTheme);
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.layout_show_info_dialog, null);

        new MultipleScreen(context);
        MultipleScreen.resizeAllView((ViewGroup) v);

        infoDialog.setContentView(v);

        Button btnOK = (Button) infoDialog.findViewById(R.id.dialog_btn_positive);
        TextView tvTitle = (TextView) infoDialog.findViewById(R.id.dialog_tv_title);
        TextView tvBody = (TextView) infoDialog.findViewById(R.id.dialog_tv_body);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        if (body == null) {
            tvBody.setVisibility(View.GONE);
        } else {
            tvBody.setText(body);
        }

        if (action != null) {
            btnOK.setText(action);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
                if (dialogCallback != null) {
                    dialogCallback.onAction1();
                }
                infoDialog.dismiss();
            }
        });

        infoDialog.show();
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    public static void showLoadingProgress(Context context) {
        if (CustomProgressDialog.sPdCount <= 0) {
            CustomProgressDialog.sPdCount = 0;
            sPdLoading = null;
            sPdLoading = new CustomProgressDialog(context, R.style.CustomDialogTheme);
            sPdLoading.show();
            if (Build.VERSION.SDK_INT > 10) {
                LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View loadingV = inflator.inflate(R.layout.layout_pd_loading, null);
                new MultipleScreen(context);
                MultipleScreen.resizeAllView((ViewGroup) loadingV);
                sPdLoading.setContentView(loadingV);
            } else {
                String message = context.getResources().getString(R.string.common_loading);
            }
            CustomProgressDialog.sPdCount++;
        } else {
            CustomProgressDialog.sPdCount++;
        }
    }

    public static void dismissLoadingProgress() {
        if (CustomProgressDialog.sPdCount <= 1) {
            if (sPdLoading != null && sPdLoading.isShowing())
                sPdLoading.dismiss();
            CustomProgressDialog.sPdCount--;
        } else {
            CustomProgressDialog.sPdCount--;
        }
    }


    public static void saveCurrentStore(StoreObject storeObject){
        mStoreObject = storeObject;
    }

    public static UserObject getCurrentUser(){
        return mUserObj;
    }

    public static void saveCurrentUser(UserObject userObject){
        mUserObj = userObject;
    }

    public static StoreObject getCurrentStore(){
        return mStoreObject;
    }

    public static void parseErrors(Context mContext,HashMap<String, Object> requestBody, JSONObject jObjError) {
        try {
            for (String key : requestBody.keySet()) {
                if (jObjError.has(key)) {
                    String error = null;
                    error = jObjError.getJSONArray(key).get(0).toString();
                    Log.e("Error ", mContext.getClass().getSimpleName() + error);
                    showInfoDialog(mContext, "Failed", error, "OK", null);
                    return;
                }
            }
            if (jObjError.has("non_field_errors")) {
                String error = jObjError.getJSONArray("non_field_errors").get(0).toString();
                if (error != null) {
                    showInfoDialog(mContext, "Failed", error, "OK", null);
                    return;
                }
            }

            if (jObjError.has("detail")) {
                String error = jObjError.getString("detail");
                showInfoDialog(mContext, "Failed", error, "OK", null);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String formatDate(String dateInString){
        String returnStr = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date date = formatter.parse(dateInString);
            returnStr = formatter2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnStr;

    }
}
