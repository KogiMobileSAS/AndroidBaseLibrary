package com.kogimobile.android.baselibrary.app.base;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenter;
import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenterListener;

/**
 * Created by Julian Cardona on 9/27/16.
 */

public abstract class BaseFragmentMVP<T extends BasePresenter> extends BaseFragment implements BasePresenterListener {

    protected T presenter;

    @CallSuper
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(presenter!=null){
            presenter.attachView(this);
        }else{
            throw new RuntimeException(String.format("presenter of fragment %s MUST be initialized on the initVars() method",this.getClass().getSimpleName()));
        }
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        if(presenter!=null){
            presenter.detachView();
        }
    }

}
