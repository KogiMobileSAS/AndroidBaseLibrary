package com.kogimobile.android.baselibrary.widgets;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Julian Cardona on 10/10/16.
 */
public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 3; // The minimum amount of items to have below your current scroll position before loading more.
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerViewOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading && totalItemCount != previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    onLoadMore(previousTotal);
                    loading = true;
                }
            };
            handler.post(r);

        }
    }

    public abstract void onLoadMore(int previousTotal);
}