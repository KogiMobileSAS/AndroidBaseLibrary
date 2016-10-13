package com.kogimobile.android.baselibrary.app.base.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Julian Cardona on 7/22/16.
 */
public class BasePresenter <T extends BasePresenterListener> implements Presenter<T> {

    private T viewListener;
    private CompositeSubscription subscription = new CompositeSubscription();

    public void addSubscription(Subscription serviceSubscription) {
        this.subscription.add(serviceSubscription);
    }

    @Override
    public void attachView(T mvpView) {
        viewListener = mvpView;
    }

    @Override
    public void detachView() {
        viewListener = null;
        subscription.unsubscribe();
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
