package com.kogimobile.android.baselibrary.app.base.presenter;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Julian Cardona on 7/22/16.
 */
public class BasePresenter <T extends BasePresenterListener> implements Presenter<T> {

    private T viewListener;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public void addSubscription(@NonNull Disposable serviceSubscription) {
        this.disposables.add(serviceSubscription);
    }

    @Override
    public void attachView(T mvpView) {
        viewListener = mvpView;
    }

    @Override
    public void detachView() {
        viewListener = null;
        if (this.disposables != null) {
            this.disposables.clear();
        }
    }

    public boolean isViewAttached() {
        return viewListener != null;
    }

    public T getViewListener() {
        return viewListener;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {

        private static final long serialVersionUID = 4700247243634194615L;

        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }
}
