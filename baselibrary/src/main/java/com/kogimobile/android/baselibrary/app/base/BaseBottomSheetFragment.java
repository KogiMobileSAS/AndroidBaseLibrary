package com.kogimobile.android.baselibrary.app.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;

import com.kogimobile.android.baselibrary.app.base.lifecycle.EventBusLifeCycleObserver;
import com.kogimobile.android.baselibrary.app.base.lifecycle.RxLifeObserver;
import com.kogimobile.android.baselibrary.app.busevents.utils.EventGhost;

import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Julian Cardona on 7/17/17.
 */

public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private final RxLifeObserver rxLifeObserver = new RxLifeObserver();
    private final EventBusLifeCycleObserver busLifeObserver = new EventBusLifeCycleObserver(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    public void addDisposable(Disposable disposable){
        rxLifeObserver.addDisposable(disposable);
    }

    public void addDisposableForever(Disposable disposable){
        rxLifeObserver.addDisposableForever(disposable);
    }

    public CompositeDisposable getDisposables() {
        return rxLifeObserver.getDisposables();
    }

    public CompositeDisposable getDisposablesForever() {
        return rxLifeObserver.getDisposablesForever();
    }

    abstract protected void initVars();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
        initLifeCycleObservers();
    }

    private void initLifeCycleObservers(){
        getLifecycle().addObserver(rxLifeObserver);
        getLifecycle().addObserver(busLifeObserver);
    }

    @Subscribe
    public void onEventGhost(EventGhost event) {}
}
