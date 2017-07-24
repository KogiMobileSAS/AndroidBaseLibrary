package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseFragmentMVPList;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.presenter.PresenterListenerRecyclerView;
import com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.presenter.PresenterRecyclerView;
import com.kogimobile.baselibrary.sample.databinding.FrgRecyclerviewBinding;
import com.kogimobile.baselibrary.sample.entities.Item;

import java.util.List;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgRecyclerView extends BaseFragmentMVPList<PresenterRecyclerView, Item> implements PresenterListenerRecyclerView {

    private FrgRecyclerviewBinding binding;

    public static FrgRecyclerView newInstance() {
        FrgRecyclerView fragment = new FrgRecyclerView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVars() {
        setLoadMoreEnabled(true);
        setPresenter(new PresenterRecyclerView());
        setAdapter(new AdapterItems());
        doLoadItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.frg_recyclerview, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        setRecyclerView(this.binding.recyclerView);
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        getRecyclerView().setHasFixedSize(true);
        this.binding.swipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doRefreshItems();
                    }
                }
        );
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDoLoadItems() {
        getPresenter().doLoadListItems(false);
    }

    @Override
    protected void onDoLoadMoreItems() {
        getPresenter().doLoadMoreListItems();
    }

    @Override
    protected void onDoRefreshItems() {
        getPresenter().doLoadListItems(true);
    }

    @Override
    protected void onLoadItemsFinished(@NonNull List<Item> list) {

    }

    @Override
    protected void onLoadMoreItemsFinished(@NonNull List<Item> list) {

    }

    @Override
    protected void onRefreshItemsFinished(@NonNull List<Item> list) {
        if (this.binding.swipeRefresh != null) {
            this.binding.swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onListItemsLoadFail() {

    }

    @Override
    public void onListItemsLoadMoreFail() {

    }

    @Override
    public void onListItemsRefreshFail() {

    }
}