package com.kogimobile.android.baselibrary.app.base.life_cycle_observers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Julian Cardona on 6/12/17.
 */

public class EventBusLifeCycleObserver implements LifecycleObserver {

    private Object subscriber;

    public EventBusLifeCycleObserver(Object subscriber){
        this.subscriber = subscriber;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onStart(){
        EventBus.getDefault().register(subscriber);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        EventBus.getDefault().unregister(subscriber);
    }

}
