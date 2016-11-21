package com.kogimobile.android.baselibrary.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kogimobile.android.baselibrary.app.busevents.EventGhost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Julian Cardona on 11/14/14.
 */
public abstract class BaseFragment extends Fragment {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private Unbinder unbinder;

    public void addSubscription(@NonNull Disposable serviceSubscription) {
        this.disposables.add(serviceSubscription);
    }

    abstract protected void initVars();

    @CallSuper
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
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
        unbinder = ButterKnife.bind(this, view);
        initViews();
        initListeners();
    }

    abstract protected void initViews();

    abstract protected void initListeners();

    @Subscribe
    public void onEventGhost(EventGhost event){

    }

    @CallSuper
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        if (this.disposables != null) {
            this.disposables.clear();
        }
    }

}
