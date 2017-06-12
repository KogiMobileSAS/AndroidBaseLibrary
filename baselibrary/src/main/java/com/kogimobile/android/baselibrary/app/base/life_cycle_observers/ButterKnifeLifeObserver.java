package com.kogimobile.android.baselibrary.app.base.life_cycle_observers;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author juliancardona on 6/10/17.
 */

public class ButterKnifeLifeObserver implements LifecycleObserver {

    private final Unbinder unbinder ;

    public ButterKnifeLifeObserver(@NonNull Object targetClass, View view) {
        unbinder = ButterKnife.bind(targetClass,view);
    }

    public ButterKnifeLifeObserver(Activity activity) {
        unbinder = ButterKnife.bind(activity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        unbinder.unbind();
    }

}
