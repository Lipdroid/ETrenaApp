package com.dtmweb.etrendapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.SellerObject;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {
    private CorrectSizeUtil mCorrectSize = null;
    private ProgressBar mProgressBar;
    private int myProgress = 0;
    private Context mContext;
    private static int WAIT_TIME_MILLIS = 30;
    private static String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        TAG = this.getClass().getSimpleName();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        new Thread(myThread).start();
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }


    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            //TODO Auto -generated method stub
            while (myProgress < 100) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(WAIT_TIME_MILLIS);
                } catch (Throwable t) {
                }
            }

        }

        Handler myHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                myProgress++;
                mProgressBar.setProgress(myProgress);
                if (myProgress == 100) {
                    //completed now start the activity
                    goToMainPage();
                    finish();
                }
            }
        };
    };

    private void goToMainPage() {
        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }


}

