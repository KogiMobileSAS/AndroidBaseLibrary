package com.kogimobile.android.baselibrary.app.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kogimobile.android.baselibrary.app.base.life_cycle_observers.ButterKnifeLifeObserver;
import com.kogimobile.android.baselibrary.app.base.life_cycle_observers.RxLifeObserver;
import com.kogimobile.android.baselibrary.app.busevents.EventGhost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.Disposable;

/**
 * @author Julian Cardona. julian@kogimobile.com
 * @version 9/4/16. *
 *          Copyright 2015 Kogi Mobile
 *          <p>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p>
 *          http://www.apache.org/licenses/LICENSE-2.0
 *          <p>
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 * @modified Pedro Scott. pedro@kogimobile.com
 */
public abstract class BaseFragment extends Fragment implements LifecycleOwner{

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private RxLifeObserver rxLifeObserver = new RxLifeObserver();

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }

    public void addDispossable(Disposable disposable){
        rxLifeObserver.addDisposable(disposable);
    }

    abstract protected void initVars();

    @CallSuper
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        getLifecycle().addObserver(rxLifeObserver);
        initVars();
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(new ButterKnifeLifeObserver(getContext(),view));
        initViews();
        initListeners();
    }

    abstract protected void initViews();

    abstract protected void initListeners();

    @CallSuper
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEventGhost(EventGhost event) {
    }

}
