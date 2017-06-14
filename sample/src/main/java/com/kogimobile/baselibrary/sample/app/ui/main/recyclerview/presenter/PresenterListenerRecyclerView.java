package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.presenter;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BasePresenterListListener;
import com.kogimobile.baselibrary.sample.entities.Item;

/**
 * Created by juliancardona on 3/11/17.
 */

public interface PresenterListenerRecyclerView extends BasePresenterListListener<Item> {

    void onListItemsLoadFail();

    void onListItemsLoadMoreFail();

    void onListItemsRefreshFail();

}
