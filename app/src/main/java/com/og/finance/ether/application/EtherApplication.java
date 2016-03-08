package com.og.finance.ether.application;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by olivier.goutay on 3/7/16.
 */
public class EtherApplication extends Application {

    /**
     * Saving the app context
     */
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

        Fabric.with(this, new Crashlytics());
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
