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
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Julian Cardona on 11/14/14.
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private CompositeSubscription subscription;

    private CompositeSubscription getSubscription() {
        return subscription;
    }

    public void addSubscription(@NonNull Subscription serviceSubscription) {
        this.subscription.add(serviceSubscription);
    }

    abstract protected void initVars();

    @CallSuper
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        subscription = new CompositeSubscription();
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
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

}
