package com.kogimobile.android.baselibrary.app.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenter;
import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenterListener;

/**
 * @author Julian Cardona on 1/10/17.
 */

public abstract class BaseActivityMVP<T extends BasePresenter> extends BaseActivity implements BasePresenterListener {

    private T presenter;

    public BaseActivityMVP() {
    }

    public T getPresenter() {
        return this.presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(this.presenter != null) {
            this.presenter.attachView(this);
        } else {
            throw new RuntimeException(String.format("Presenter of Activity %s MUST be initialized on the initVars() method by setPresenter()", new Object[]{this.getClass().getSimpleName()}));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.presenter != null) {
            this.presenter.detachView();
        }
    }

}
