package com.kogimobile.android.baselibrary.app.base.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kogimobile.android.baselibrary.app.base.BaseFragmentMVP;
import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenter;

import java.util.List;

/**
 * @author Julian Cardona on 10/10/16.
 *
 * @modified Pedro Scott. predro@kogimobile.com
 */
public abstract class BaseFragmentMVPList<P extends BasePresenter, M> extends BaseFragmentMVP<P> implements BasePresenterListListener<M> {

    private boolean isHeaderEnabled;
    private boolean isLoadEnabled = true;
    private boolean isLoadMoreEnabled;
    private boolean isRefreshEnabled;
    private RecyclerView recyclerView;
    private BaseAdapter<M, BaseAdapter.BaseViewHolder<M>> adapter;

    public boolean isHeaderEnabled() {
        return isHeaderEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        isHeaderEnabled = headerEnabled;
        if(getAdapter() != null){
            getAdapter().setHeaderEnabled(headerEnabled);
        }
    }

    public boolean isLoadEnabled() {
        return isLoadEnabled;
    }

    public void setLoadEnabled(boolean loadEnabled) {
        isLoadEnabled = loadEnabled;
        if(getAdapter() != null){
            getAdapter().setLoadEnabled(loadEnabled);
        }
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
        if(getAdapter() != null){
            getAdapter().setLoadMoreEnabled(loadMoreEnabled);
        }
    }

    public boolean isRefreshEnabled() {
        return isRefreshEnabled;
    }

    public void setRefreshEnabled(boolean refreshEnabled) {
        isRefreshEnabled = refreshEnabled;
        if(getAdapter() != null){
            getAdapter().setRefreshEnabled(refreshEnabled);
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setAdapter(@NonNull BaseAdapter<M, BaseAdapter.BaseViewHolder<M>> adapter) {
        this.adapter = adapter;
    }

    public BaseAdapter<M, BaseAdapter.BaseViewHolder<M>> getAdapter() {
        return adapter;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getAdapter() != null) {
            getAdapter().setHeaderEnabled(isHeaderEnabled());
            getAdapter().setLoadEnabled(isLoadEnabled());
            getAdapter().setLoadMoreEnabled(isLoadMoreEnabled());
            getAdapter().setRefreshEnabled(isRefreshEnabled());
        } else {
            throw new RuntimeException(String.format("Adapter of the RecyclerView %s MUST be initialized on the initVars() method by the setAdapter()", this.getClass().getSimpleName()));
        }

        if (getRecyclerView() == null) {
            throw new RuntimeException(String.format("RecyclerView of the view %s MUST be setter on the initViews() method by the setRecyclerView()", this.getClass().getSimpleName()));
        }
        getRecyclerView().setAdapter(getAdapter());
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = getAdapter().getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                if (getAdapter().isLoadMoreEnabled() && !isLoadingElements() && lastVisibleItemPosition == totalItemCount - 1) {
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

    /**
     * Make the fragment trigger the first load of data on the adapter
     */
    @CallSuper
    protected void doLoadItems() {
        getAdapter().setLoading(true);
        getAdapter().clearItems();
        getAdapter().notifyDataSetChanged();
        getAdapter().checkLoadingViewState();
        onDoLoadItems();
    }

    /**
     * Make the fragment trigger load more data on the adapter
     */
    @CallSuper
    protected void doLoadMoreItems() {
        getAdapter().setLoadingMore(true);
        getAdapter().checkLoadingMoreViewState();
        getRecyclerView().smoothScrollToPosition(getAdapter().getItemCount() - 1);
        onDoLoadMoreItems();
    }

    /**
     * Make the fragment trigger refresh data on the adapter
     */
    @CallSuper
    protected void doRefreshItems() {
        getAdapter().setLoadMoreEnabled(false);
        getAdapter().setRefreshing(true);
        onDoRefreshItems();
    }

    @CallSuper
    protected void doCancelLoading() {
        getAdapter().setLoading(false);
        getAdapter().setLoadingMore(false);
        getAdapter().setRefreshing(false);
        getAdapter().notifyDataSetChanged();
    }

    @CallSuper
    @Override
    public void itemsLoaded(@NonNull List<M> items,boolean isThereMoreDataToLoad) {
        getAdapter().setLoadMoreEnabled(isThereMoreDataToLoad);
        getAdapter().setLoading(false);
        getAdapter().checkLoadingViewState();
        getAdapter().refreshItems(items);
        onLoadItemsFinished(items);
    }

    @CallSuper
    @Override
    public void itemsLoaded(@NonNull List<M> items) {
        itemsLoaded(items,false);
    }

    @CallSuper
    @Override
    public void moreItemsLoaded(@NonNull List<M> moreItems,boolean isThereMoreDataToLoad) {
        getAdapter().setLoadMoreEnabled(isThereMoreDataToLoad);
        getAdapter().setLoadingMore(false);
        getAdapter().checkLoadingMoreViewState();
        getAdapter().addItems(moreItems);
        onLoadMoreItemsFinished(moreItems);
    }

    @CallSuper
    @Override
    public void moreItemsLoaded(@NonNull List<M> moreItems) {
        moreItemsLoaded(moreItems,false);
    }

    @CallSuper
    @Override
    public void refreshItemsLoaded(@NonNull List<M> refreshItems,boolean isThereMoreDataToLoad) {
        getAdapter().setLoadMoreEnabled(isThereMoreDataToLoad);
        getAdapter().setRefreshing(false);
        getAdapter().refreshItems(refreshItems);
        onRefreshItemsFinished(refreshItems);
    }

    @CallSuper
    @Override
    public void refreshItemsLoaded(@NonNull List<M> refreshItems) {
        refreshItemsLoaded(refreshItems,false);
    }

    public boolean isLoadingElements() {
        return getAdapter().isLoading() || getAdapter().isLoadingMore() || getAdapter().isRefreshing();
    }

    /**
     * Callback after {@link #doLoadItems()} doLoadItems() method is invoked
     */
    protected abstract void onDoLoadItems();

    /**
     * Callback after {@link #onDoLoadMoreItems()} doLoadMoreItems() method is invoked
     */
    protected abstract void onDoLoadMoreItems();

    /**
     * Callback after {@link #onDoLoadItems()} doRefreshItems() method is invoked
     */
    protected abstract void onDoRefreshItems();

    /**
     * Callback after {@link #onDoLoadItems()} onDoLoadItems() method finish Successfully
     */
    protected abstract void onLoadItemsFinished(@NonNull List<M> items);

    /**
     * Callback after {@link #onDoLoadMoreItems()} onDoLoadMoreItems() method finish Successfully
     */
    protected abstract void onLoadMoreItemsFinished(@NonNull List<M> newItems);

    /**
     * Callback after {@link #onDoRefreshItems()} onDoRefreshItems() method finish Successfully
     */
    protected abstract void onRefreshItemsFinished(@NonNull List<M> items);

}

