package com.kogimobile.android.baselibrary.app.base.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Julian Cardona on 10/10/16.
 *
 * @modified Pedro Scott. predro@kogimobile.com
 */
public abstract class BaseFragmentRecyclerView<M> extends BaseFragment {

    private boolean isHeaderEnabled;
    private boolean isLoadEnabled = true;
    private boolean isLoadMoreEnabled;
    private boolean isRefreshEnabled;
    private BaseAdapter<M, BaseAdapter.BaseViewHolder<M>> adapter;

    public boolean isHeaderEnabled() {
        return isHeaderEnabled;
    }

    protected void setHeaderEnabled(boolean headerEnabled) {
        isHeaderEnabled = headerEnabled;
        if(getAdapter() != null){
            getAdapter().setHeaderEnabled(headerEnabled);
        }
    }

    public boolean isLoadEnabled() {
        return isLoadEnabled;
    }

    protected void setLoadEnabled(boolean loadEnabled) {
        isLoadEnabled = loadEnabled;
        if(getAdapter() != null){
            getAdapter().setLoadEnabled(loadEnabled);
        }
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    protected void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
        if(getAdapter() != null){
            getAdapter().setLoadMoreEnabled(loadMoreEnabled);
        }
    }

    public boolean isRefreshEnabled() {
        return isRefreshEnabled;
    }

    protected void setRefreshEnabled(boolean refreshEnabled) {
        isRefreshEnabled = refreshEnabled;
        if(getAdapter() != null){
            getAdapter().setRefreshEnabled(refreshEnabled);
        }
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

    protected abstract @NonNull RecyclerView getRecyclerView();

    /**
     * Make the fragment trigger the first load of data on the adapter
     */
    @CallSuper
    protected void doLoadItems() {
        getAdapter().setLoading(true);
        getAdapter().clearItems();
        getAdapter().notifyDataSetChanged();
        getAdapter().checkLoadingViewState();
        getItemsFromSource()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(getFuncActionItemsLoaded())
                .subscribe(onLoadItemsSuccess(),onLoadItemsFail());
    }

    @NonNull
    private Function<Pair<List<M>, Boolean>, Single<Pair<List<M>, Boolean>>> getFuncActionItemsLoaded() {
        return new Function<Pair<List<M>, Boolean>, Single<Pair<List<M>, Boolean>>>() {
            @Override
            public Single<Pair<List<M>, Boolean>> apply(Pair< List<M>, Boolean> pair) throws Exception {
                itemsLoaded(pair.first, pair.second);
                return Single.just(pair);
            }
        };
    }

    private void itemsLoaded(@NonNull List<M> items,boolean isThereMoreDataToLoad) {
        getAdapter().setLoadMoreEnabled(isThereMoreDataToLoad);
        getAdapter().setLoading(false);
        getAdapter().checkLoadingViewState();
        getAdapter().refreshItems(items);
    }

    /**
     * get pair that contains the items from a source and a boolean
     * that indicates if it has more items pending to load for the first batch of loading
     */
    protected abstract @NonNull Single<Pair<List<M>,Boolean>> getItemsFromSource();

    protected @NonNull Consumer<Pair<List<M>,Boolean>> onLoadItemsSuccess(){
        return new Consumer<Pair<List<M>, Boolean>>() {
            @Override
            public void accept(Pair<List<M>, Boolean> listBooleanPair) throws Exception {

            }
        };
    }

    @CallSuper
    protected @NonNull Consumer<Throwable> onLoadItemsFail(){
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                doCancelLoading();
            }
        };
    }

    /**
     * Make the fragment trigger load more data on the adapter
     */
    private void doLoadMoreItems() {
        getAdapter().setLoadingMore(true);
        getAdapter().checkLoadingMoreViewState();
        getRecyclerView().smoothScrollToPosition(getAdapter().getItemCount() - 1);
        getMoreItemsFromSource()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(getFuncActionMoreItemsLoaded())
                .subscribe(onLoadMoreItemsSuccess(),onLoadMoreItemsFail());
    }

    @NonNull
    private Function<Pair<List<M>, Boolean>, Single<Pair<List<M>, Boolean>>> getFuncActionMoreItemsLoaded() {
        return new Function<Pair<List<M>, Boolean>, Single<Pair<List<M>, Boolean>>>() {
            @Override
            public Single<Pair<List<M>, Boolean>> apply(Pair< List<M>, Boolean> pair) throws Exception {
                moreItemsLoaded(pair.first, pair.second);
                return Single.just(pair);
            }
        };
    }

    private void moreItemsLoaded(@NonNull List<M> moreItems,boolean isThereMoreDataToLoad) {
        getAdapter().setLoadMoreEnabled(isThereMoreDataToLoad);
        getAdapter().setLoadingMore(false);
        getAdapter().checkLoadingMoreViewState();
        getAdapter().addItems(moreItems);
    }

    /**
     * get pair that contains the items from a source and a boolean
     * that indicates if it has more items pending to load for the first batch of loading
     */
    protected abstract @NonNull Single<Pair<List<M>,Boolean>> getMoreItemsFromSource();

    protected @NonNull Consumer<Pair<List<M>,Boolean>> onLoadMoreItemsSuccess(){
        return new Consumer<Pair<List<M>, Boolean>>() {
            @Override
            public void accept(Pair<List<M>, Boolean> listBooleanPair) throws Exception {

            }
        };
    }

    @CallSuper
    protected @NonNull Consumer<Throwable> onLoadMoreItemsFail(){
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                doCancelLoading();
            }
        };
    }

    /**
     * Make the fragment trigger refresh data on the adapter
     */
    @CallSuper
    protected void doRefreshItems() {
        getAdapter().setLoadMoreEnabled(false);
        getAdapter().setRefreshing(true);
        getItemsFromSource().
                observeOn(AndroidSchedulers.mainThread())
                .flatMap(getFuncActionRefreshItems())
                .subscribe(onRefreshItemsLoadSuccess(),onRefreshItemsLoadFail());
    }

    @NonNull
    private Function<Pair<List<M>, Boolean>, Single<Pair<List<M>, Boolean>>> getFuncActionRefreshItems() {
        return new Function<Pair<List<M>, Boolean>, Single<Pair<List<M>, Boolean>>>() {
            @Override
            public Single<Pair<List<M>, Boolean>> apply(Pair< List<M>, Boolean> pair) throws Exception {
                refreshItemsLoaded(pair.first, pair.second);
                return Single.just(pair);
            }
        };
    }

    private void refreshItemsLoaded(@NonNull List<M> refreshItems, boolean isThereMoreDataToLoad) {
        getAdapter().setLoadMoreEnabled(isThereMoreDataToLoad);
        getAdapter().setRefreshing(false);
        getAdapter().refreshItems(refreshItems);
    }

    protected @NonNull Consumer<Pair<List<M>,Boolean>> onRefreshItemsLoadSuccess(){
        return new Consumer<Pair<List<M>, Boolean>>() {
            @Override
            public void accept(Pair<List<M>, Boolean> listBooleanPair) throws Exception {

            }
        };
    }

    @CallSuper
    protected @NonNull Consumer<Throwable> onRefreshItemsLoadFail(){
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                doCancelLoading();
            }
        };
    }

    @CallSuper
    protected void doCancelLoading() {
        getAdapter().setLoading(false);
        getAdapter().setLoadingMore(false);
        getAdapter().setRefreshing(false);
        getAdapter().notifyDataSetChanged();
    }

    public boolean isLoadingElements() {
        return getAdapter().isLoading() || getAdapter().isLoadingMore() || getAdapter().isRefreshing();
    }

}

