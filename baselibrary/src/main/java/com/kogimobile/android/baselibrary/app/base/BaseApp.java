package com.kogimobile.android.baselibrary.app.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Julian Cardona on 2/2/15.
 */
public class BaseApp extends MultiDexApplication {

    private static Context globalContext;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
    }

    public static Context getGlobalContext() {
        return globalContext;
    }
}
