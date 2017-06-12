package com.kogimobile.baselibrary.sample.app;

import com.kogimobile.android.baselibrary.app.base.BaseApp;

import timber.log.Timber;

/**
 * Created by Julian Cardona on 2/2/15.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber(){
        Timber.plant(
                new Timber.DebugTree() {
                    // Add the line number to the tag.
                    @Override
                    protected String createStackElementTag(StackTraceElement element) {
                        return super.createStackElementTag(element) + ':' + element.getLineNumber();
                    }
                });
    }

}