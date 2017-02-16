package com.kogimobile.android.baselibrary.app.base.list;

import android.os.Bundle;
import android.os.Handler;
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

import java.util.List;

import timber.log.Timber;

/**
 * @author Julian Cardona on 10/10/16.
 *
 * @modified Pedro Scott. predro@kogimobile.com
 */
public abstract class BaseFragmentMVPList<P extends BasePresenter, M> extends BaseFragmentMVP<P> implements BasePresenterListListener<M> {

    private static final int DEFAULT_ITEMS_PER_PAGE = 10;

    private int maxLoadMoreItemsPerPage = DEFAULT_ITEMS_PER_PAGE;
    private int lastPageItemsCount = 0;
    private boolean isLoading = true;
    private boolean isLoadingMore = false;
    private boolean loadMoreEnabled = false;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private BaseSimpleAdapter<M, BaseSimpleAdapter.BaseViewHolder<M>> adapter;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setAdapter(@NonNull BaseSimpleAdapter<M, BaseSimpleAdapter.BaseViewHolder<M>> adapter) {
        this.adapter = adapter;
    }

    public BaseSimpleAdapter<M, BaseSimpleAdapter.BaseViewHolder<M>> getAdapter() {
        return adapter;
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

    /**
     * Set the number of items that the load more function going to get.
     *
     * @param maxLoadMoreItemsPerPage number of items
     */
    public void setMaxLoadMoreItemsPerPage(int maxLoadMoreItemsPerPage) {
        this.maxLoadMoreItemsPerPage = maxLoadMoreItemsPerPage;
    }

    public boolean isLoadMoreEnabled() {
        return loadMoreEnabled;
    }

    /**
     * Active the load more functionality and set number of items to get for each search.
     *
     * @param loadMoreEnabled active load more mode.
     */
    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getSwipeRefresh() != null) {
            getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doLoadItems();
                }
            });
        } else {
            throw new RuntimeException(String.format("SwipeRefresh of the view %s MUST be setter on the initViews() method by the setSwipeRefresh()", this.getClass().getSimpleName()));
        }
        if (recyclerView == null) {
            throw new RuntimeException(String.format("RecyclerView of the view %s MUST be setter on the initViews() method by the setRecyclerView()", this.getClass().getSimpleName()));
        }

        if (isLoadMoreEnabled()) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalItemCount = getAdapter().getItemCount();
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                    if (!isLoadingElements() && isAbleToLoadMore() && lastVisibleItemPosition == totalItemCount - 1) {
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                doLoadMoreItems();
                            }
                        };
                        handler.post(r);
                    }

                }
            });
        }
        if (adapter != null) {
            adapter.setLoadMoreEnabled(true);
            recyclerView.setAdapter(adapter);
        } else {
            throw new RuntimeException(String.format("Adapter of the RecyclerView %s MUST be initialized on the initVars() method by the setAdapter()", this.getClass().getSimpleName()));
        }

    }

    @CallSuper
    protected boolean isAbleToLoadMore() {
        boolean enable = lastPageItemsCount >= maxLoadMoreItemsPerPage && isLoadMoreEnabled();
        if(adapter != null){
            adapter.setLoadMoreEnabled(enable);
        }
        return enable;
    }

    /**
     * This method is used to load the first batch of items.
     */
    @CallSuper
    protected void doLoadItems() {
        this.isLoading = true;
        if (adapter != null) {
            if(getSwipeRefresh() != null && !getSwipeRefresh().isRefreshing()) {
                adapter.showLoadingView(true);
            }
            onDoLoadItems();
            Timber.d("Loading items ...");
        }
    }

    /**
     * This method is used to load more items.
     */
    @CallSuper
    protected void doLoadMoreItems() {
        this.isLoadingMore = true;
        if (adapter != null) {
            adapter.showLoadingMoreView(true);
            getRecyclerView().smoothScrollToPosition(adapter.getItemCount() - 1);
            onDoLoadMoreItems();
            Timber.d("Loading More items ...");
        }
    }

    @CallSuper
    @Override
    public void itemsLoaded(List<M> items) {
        this.isLoading = false;

        if (adapter != null) {
            adapter.cleanItemsAndUpdate(items);
            if(getSwipeRefresh() != null && !getSwipeRefresh().isRefreshing()){
                adapter.showLoadingView(false);
            }
        }

        if (getSwipeRefresh() != null && getSwipeRefresh().isRefreshing()) {
            getSwipeRefresh().setRefreshing(false);
        }

        if(items != null){
            lastPageItemsCount = items.size();
            isAbleToLoadMore();
            onItemsLoaded(items);
        }
        Timber.d("%d items loaded", items.size());
    }

    @CallSuper
    @Override
    public void moreItemsLoaded(List<M> newItems) {
        this.isLoadingMore = false;
        if (adapter != null && newItems != null){
            adapter.showLoadingMoreView(false);
            adapter.addItems(newItems);
        }

        if(newItems != null){
            lastPageItemsCount = newItems.size();
            isAbleToLoadMore();
            onMoreItemsLoaded(newItems);
        }
        Timber.d("%d more items loaded", newItems.size());
    }

    @CallSuper
    protected void doCancelLoading() {
        this.isLoading = false;
        this.isLoadingMore = false;
        if (getSwipeRefresh() != null && getSwipeRefresh().isRefreshing()) {
            getSwipeRefresh().setRefreshing(false);
        }
        if (adapter != null) {
            if(isLoading != adapter.isLoading() && getSwipeRefresh() != null && !getSwipeRefresh().isRefreshing()) {
                adapter.showLoadingView(false);
            }

            if(isLoadingMore != adapter.isLoadingMore()) {
                adapter.showLoadingMoreView(false);
            }
        }
        Timber.d("Items Stop loading");
    }

    public boolean isLoadingElements() {
        return this.isLoading || this.isLoadingMore;
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

