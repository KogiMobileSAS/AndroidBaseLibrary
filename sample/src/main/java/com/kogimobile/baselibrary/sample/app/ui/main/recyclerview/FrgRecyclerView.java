package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseFragmentRecyclerView;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.databinding.FrgRecyclerviewBinding;
import com.kogimobile.baselibrary.sample.entities.Item;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgRecyclerView extends BaseFragmentRecyclerView<Item>{

    private FrgRecyclerviewBinding binding;
    private ViewModelRecyclerView viewModel;

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
        this.viewModel = ViewModelProviders.of(this).get(ViewModelRecyclerView.class);
        doLoadItems();
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return this.binding.recyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.frg_recyclerview, container, false);
        return this.binding.getRoot();
    }

    @Override
    protected void initViews() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        getRecyclerView().setHasFixedSize(true);
    }

    @Override
    protected void initListeners() {
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
    protected Single<Pair<List<Item>, Boolean>> getItemsFromSource() {
        return this.viewModel.getListItems();
    }

    @Override
    protected Single<Pair<List<Item>, Boolean>> getMoreItemsFromSource() {
        return this.viewModel.getMoreListItems();
    }

    @NonNull
    @Override
    protected Consumer<Pair<List<Item>, Boolean>> onRefreshItemsLoadSuccess() {
        return new Consumer<Pair<List<Item>, Boolean>>() {
            @Override
            public void accept(Pair<List<Item>, Boolean> listBooleanPair) throws Exception {
                hideRefresh();
            }
        };
    }

    @NonNull
    @Override
    protected Consumer<Throwable> onRefreshItemsLoadFail() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                hideRefresh();
            }
        };
    }

    private void hideRefresh(){
        this.binding.swipeRefresh.setRefreshing(false);
    }
}