package com.dtmweb.etrendapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dtmweb.etrendapp.utils.ImageUtils;


/**
 * Created by lipuhossain on 1/25/17.
 */

public class EtrendApplication extends Application {

    private final static String LOG_TAG = Application.class.getSimpleName();
    private static Context sContext = null;

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        sContext = EtrendApplication.this;
        new ImageUtils(sContext);
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");

    }

    public static Context AudienceApplication() {
        return sContext;
    }

    // uncaught exception handler variable
    private Thread.UncaughtExceptionHandler defaultUEH;

    // handler listener
//    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
//            new Thread.UncaughtExceptionHandler() {
//                @Override
//                public void uncaughtException(Thread thread, Throwable ex) {
//
//                    // here I do logging of exception to a db
//                    PendingIntent myActivity = PendingIntent.getActivity(getApplicationContext(),
//                            192837, new Intent(getApplicationContext(), SplashActivity.class),
//                            PendingIntent.FLAG_ONE_SHOT);
//
//                    AlarmManager alarmManager;
//                    alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                            15000, myActivity );
//                    System.exit(2);
//
//                    // re-throw critical exception further to the os (important)
//                    defaultUEH.uncaughtException(thread, ex);
//                }
//            };
//
//    public LimeApplication() {
//        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
//        // setup handler for uncaught exception
//        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
//    }


}