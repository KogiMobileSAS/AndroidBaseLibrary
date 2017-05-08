package com.kogimobile.android.baselibrary.app.base;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenter;
import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenterListener;

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
public abstract class BaseFragmentMVP<T extends BasePresenter> extends BaseFragment implements BasePresenterListener {

    private T presenter;

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @CallSuper
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (presenter != null) {
            presenter.attachView(this);
        } else {
            throw new RuntimeException(String.format("Presenter of fragment %s MUST be initialized on the initVars() method by setPresenter()", this.getClass().getSimpleName()));
        }
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
