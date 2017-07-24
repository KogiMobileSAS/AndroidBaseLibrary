package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseFragmentRecyclerView;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.databinding.FrgRecyclerviewBinding;
import com.kogimobile.baselibrary.sample.entities.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgRecyclerView extends BaseFragmentRecyclerView<Item>{

    private FrgRecyclerviewBinding binding;
    private ViewModelRecyclerView viemodel;

    public static FrgRecyclerView newInstance() {
        FrgRecyclerView fragment = new FrgRecyclerView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVars() {
        setLoadMoreEnabled(true);
        setAdapter(new AdapterItems());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viemodel = ViewModelProviders.of(this).get(ViewModelRecyclerView.class);
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
        this.viemodel.getListItems()
                .subscribe(
                        new Consumer<ArrayList<Item>>() {
                               @Override
                               public void accept(ArrayList<Item> items) throws Exception {
                                   itemsLoaded(items,viemodel.hasMoreToLoad());
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                onLoadItemsFail();
                            }
                        }
                );
    }

    @Override
    protected void onDoLoadMoreItems() {
        this.viemodel.getMoreListItems()
                .subscribe(
                        new Consumer<ArrayList<Item>>() {
                            @Override
                            public void accept(ArrayList<Item> items) throws Exception {
                                moreItemsLoaded(items,viemodel.hasMoreToLoad());
                                onLoadMoreItemsFinished(items);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                onLoadMoreItemsFail();
                            }
                        }
                );
    }

    @Override
    protected void onDoRefreshItems() {
        this.viemodel.getMoreListItems()
                .subscribe(
                        new Consumer<ArrayList<Item>>() {
                            @Override
                            public void accept(ArrayList<Item> items) throws Exception {
                                refreshItemsLoaded(items);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                onRefreshItemsLoadFail();
                            }
                        }
                );
    }

    @Override
    protected void onLoadItemsFinished(@NonNull List<Item> list) {

    }

    @Override
    public void onLoadItemsFail() {

    }

    @Override
    protected void onLoadMoreItemsFinished(@NonNull List<Item> list) {

    }

    @Override
    public void onLoadMoreItemsFail() {

    }

    @Override
    protected void onRefreshItemsFinished(@NonNull List<Item> list) {
        if (this.binding.swipeRefresh != null) {
            this.binding.swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onRefreshItemsLoadFail() {

    }

}