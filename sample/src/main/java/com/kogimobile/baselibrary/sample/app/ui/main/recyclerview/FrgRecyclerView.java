package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseFragmentMVPList;
import com.kogimobile.android.baselibrary.app.busevents.snackbar.EventSnackbarMessage;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.presenter.PresenterListenerRecyclerView;
import com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.presenter.PresenterRecyclerView;
import com.kogimobile.baselibrary.sample.entities.Theme;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgRecyclerView extends BaseFragmentMVPList<PresenterRecyclerView, Theme> implements PresenterListenerRecyclerView {

    @BindView(R.id.sRLFrgRecyclerView)
    SwipeRefreshLayout sRLFrgRecyclerView;
    @BindView(R.id.rVFrgRecyclerView)
    RecyclerView rVFrgRecyclerView;

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
        setAdapter(new AdapterHome());
        doLoadItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_recyclerview, container, false);
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
        setRecyclerView(rVFrgRecyclerView);
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        getRecyclerView().setHasFixedSize(true);
        this.sRLFrgRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefreshItems();
            }
        });
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
        getPresenter().doLoadThemes(false);
    }

    @Override
    protected void onDoLoadMoreItems() {
        getPresenter().doLoadMoreThemes();
    }

    @Override
    protected void onDoRefreshItems() {
        getPresenter().doLoadThemes(true);
    }

    @Override
    protected void onLoadItemsFinished(@NonNull List<Theme> list) {

    }

    @Override
    protected void onLoadMoreItemsFinished(@NonNull List<Theme> list) {

    }

    @Override
    protected void onRefreshItemsFinished(@NonNull List<Theme> list) {
        if (this.sRLFrgRecyclerView != null) {
            this.sRLFrgRecyclerView.setRefreshing(false);
        }
    }

    @Override
    public void onThemesLoadFail() {

    }

    @Override
    public void onThemesLoadMoreFail() {

    }

    @Override
    public void onThemesRefreshFail() {

    }

    @Override
    public void onShowMessage(String message) {
        EventBus.getDefault()
                .post(
                        EventSnackbarMessage.getBuilder()
                                .withMessage(message)
                                .build()
                );
    }
}