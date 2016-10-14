package com.kogimobile.android.baselibrary.app.base.list;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kogimobile.android.baselibrary.app.base.BaseFragmentMVP;
import com.kogimobile.android.baselibrary.app.base.adapter.BaseSimpleAdapter;
import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenter;
import com.kogimobile.android.baselibrary.widgets.EndlessRecyclerViewOnScrollListener;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Julian Cardona on 10/10/16.
 */

public abstract class BaseFragmentMVPList<P extends BasePresenter, M> extends BaseFragmentMVP<P> implements BasePresenterListListener<M> {

    private static final int DEFAULT_ITEMS_PER_PAGE = 10;
    private static final int DEFAULT_MIN_ITEMS_BELOW = 5;

    private int maxLoadMoreItemsPerPage = DEFAULT_ITEMS_PER_PAGE;
    private int minItemsBelowToLoadMore = DEFAULT_MIN_ITEMS_BELOW;
    private int lastItemsCount = 0;
    private boolean isLoading = false;
    private boolean isLoadingMore = false;
    private boolean loadMoreEnabled = false;

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefresh;
    protected BaseSimpleAdapter<M, BaseSimpleAdapter.BaseViewHolder<M>> adapter;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }

    public void setSwipeRefresh(@NonNull SwipeRefreshLayout swipeRefresh) {
        this.swipeRefresh = swipeRefresh;
    }

    public int getMaxLoadMoreItemsPerPage() {
        return maxLoadMoreItemsPerPage;
    }

    public void setMaxLoadMoreItemsPerPage(int maxLoadMoreItemsPerPage) {
        this.maxLoadMoreItemsPerPage = maxLoadMoreItemsPerPage;
    }

    public boolean isLoadMoreEnabled() {
        return loadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled,int minItemsBelowToLoadMore) {
        this.loadMoreEnabled = loadMoreEnabled;
        this.minItemsBelowToLoadMore = minItemsBelowToLoadMore;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doLoadItems();
                }
            });
        }

        if (recyclerView != null && isLoadMoreEnabled()) {
            recyclerView.addOnScrollListener(new EndlessRecyclerViewOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager(),minItemsBelowToLoadMore) {
                @Override
                public void onLoadMore(int previousTotal) {
                    if (!isLoading && !isLoadingMore && isAbleToLoadMore()) {
                        doLoadMoreItems();
                    }
                }
            });
        }

    }

    @CallSuper
    protected boolean isAbleToLoadMore() {
        return lastItemsCount >= maxLoadMoreItemsPerPage && isLoadMoreEnabled();
    }

    /**
     * This method is used to load the first batch of items.
     */
    @CallSuper
    protected void doLoadItems() {
        isLoading = true;
        if (adapter != null) {
            adapter.showLoadingState(isLoading);
        }
        onDoLoadItems();
        Timber.d("Loading items ...");
    }

    /**
     * This method is used to load more items.
     */
    @CallSuper
    protected void doLoadMoreItems() {
        isLoadingMore = true;
        if (adapter != null) {
            adapter.showLoadMoreView(isLoadingMore);
        }
        onDoLoadMoreItems();
        Timber.d("Loading More items ...");
    }

    @CallSuper
    @Override
    public void itemsLoaded(List<M> items) {
        doStopLoading();
        if (adapter != null) {
            adapter.cleanItemsAndUpdate(items);
            adapter.notifyDataSetChanged();
        }
        lastItemsCount = items.size();
        onItemsLoaded(items);
        Timber.d("%d items loaded",items.size());
    }

    @CallSuper
    @Override
    public void moreItemsLoaded(List<M> newItems) {
        doStopLoading();
        if (adapter != null) {;
            adapter.addItems(newItems);
            adapter.notifyDataSetChanged();
        }
        lastItemsCount = newItems.size();
        onMoreItemsLoaded(newItems);
        Timber.d("%d more items loaded",newItems.size());
    }

    @CallSuper
    protected void doStopLoading() {
        isLoading = false;
        isLoadingMore = false;
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(isLoading);
        }
        if (adapter != null) {
            adapter.showLoadingState(isLoading);
            adapter.showLoadMoreView(isLoadingMore);
            adapter.notifyDataSetChanged();
        }
        Timber.d("Items Stop loading");
    }

    /**
     * Callback after {@link #doLoadItems()} doLoadItems() method is invoked
     */
    protected abstract void onDoLoadItems();

    /**
     * Callback after {@link #doLoadMoreItems()} doLoadMoreItems() method is invoked
     */
    protected abstract void onDoLoadMoreItems();

    protected abstract void onItemsLoaded(List<M> items);

    protected abstract void onMoreItemsLoaded(List<M> newItems);

}