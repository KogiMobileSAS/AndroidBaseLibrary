package com.kogimobile.baselibrary.sample.app.ui.splash;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;

import com.kogimobile.android.baselibrary.app.base.BaseActivity;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.ActivityMain;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * Created by Julian Cardona on 6/23/16.
 */
public class ActivitySplash extends BaseActivity{

    static {AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);}
    private static final int DEFAULT_BLINK_DELAY_TIME = 4;
    private static final int DEFAULT_DELAY_TIME = 5;

    @BindView(R.id.iVSplashLogo)
    AppCompatImageView iVSplashLogo;

    @Override
    protected void initVars() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }

    @Override
    protected void initViews() {
        startLogoAnimation();
        AddAnimationBlink();
        startWaitingTime();
    }

    @Override
    protected void initListeners() {

    }

    private void startLogoAnimation(){
        ((Animatable)iVSplashLogo.getDrawable()).start();
    }

    private void AddAnimationBlink(){
        addDisposable(
                Observable
                        .interval(DEFAULT_BLINK_DELAY_TIME, TimeUnit.SECONDS)
                        .timeInterval()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Timed<Long>>() {
                                    @Override
                                    public void accept(Timed<Long> longTimed) throws Exception {
                                        if (!((Animatable) iVSplashLogo.getDrawable()).isRunning()) {
                                            ((Animatable) iVSplashLogo.getDrawable()).start();
                                        }
                                    }
                                }
                                ,
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                }
                        )
        );
    }

    private void startWaitingTime(){
        addDisposable(
                Observable
                        .just(true)
                        .delay(DEFAULT_DELAY_TIME,TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
                                    }
                                }
                                ,
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                }
                        )
        );
    }

}