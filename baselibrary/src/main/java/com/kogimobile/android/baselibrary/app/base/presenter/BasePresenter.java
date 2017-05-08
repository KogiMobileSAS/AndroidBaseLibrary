package com.kogimobile.android.baselibrary.app.base.presenter;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
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
public class BasePresenter<T extends BasePresenterListener> implements Presenter<T> {

    private T viewListener;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public CompositeDisposable getDisposables() {
        return disposables;
    }

    public void addDisposable(@NonNull Disposable disposable) {
        this.disposables.add(disposable);
    }

    @Override
    public void attachView(T mvpView) {
        viewListener = mvpView;
    }

    @Override
    public void detachView() {
        viewListener = null;
        getDisposables().clear();
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
