package com.kogimobile.android.baselibrary.app.base.list;

import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenterListener;

import java.util.List;

/**
 * Created by Julian Cardona on 10/10/16.
 */

public interface BasePresenterListListener<T> extends BasePresenterListener {

    void doLoadItems();

    void doLoadMoreItems();

    void moreItemsLoaded(List<T> newItems);

    void itemsLoaded(List<T> items);

    void itemsLoadFail();

}
