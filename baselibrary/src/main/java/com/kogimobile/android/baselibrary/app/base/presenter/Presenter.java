package com.kogimobile.android.baselibrary.app.base.presenter;

/**
 * Created by Julian Cardona on 7/22/16.
 */
public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}