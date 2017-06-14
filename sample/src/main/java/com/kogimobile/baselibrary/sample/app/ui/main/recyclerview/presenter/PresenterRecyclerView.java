package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.presenter;

import com.kogimobile.android.baselibrary.app.base.presenter.BasePresenter;
import com.kogimobile.baselibrary.sample.entities.Item;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

;

/**
 * Created by juliancardona on 3/11/17.
 */

public class PresenterRecyclerView extends BasePresenter<PresenterListenerRecyclerView> {

    private final int MIN_RANDOM_THEMES_TO_GENERATE = 10;
    private final int MAX_RANDOM_THEMES_TO_GENERATE = 20;
    private final int MAX_LOAD_FETCH_ITEMS_DELAY = 1;//Seconds
    private final int MAX_LOAD_MORE_FETCH_ITEMS_DELAY = 2;//Seconds

    public void doLoadListItems(final boolean isRefreshing){
        addDisposable(
                Observable.just(generateRandomThemeArray())
                        .delay(MAX_LOAD_FETCH_ITEMS_DELAY, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                new Consumer<ArrayList<Item>>() {
                                    @Override
                                    public void accept(ArrayList<Item> items) throws Exception {
                                        if (isRefreshing) {
                                            getViewListener().refreshItemsLoaded(items, true);
                                        } else {
                                            getViewListener().itemsLoaded(items, true);
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        if(isRefreshing){
                                            getViewListener().onListItemsRefreshFail();
                                        }else{
                                            getViewListener().onListItemsLoadFail();
                                        }
                                    }
                                }
                        )
        );
    }

    public void doLoadMoreListItems(){
        addDisposable(
                Observable.just(generateRandomThemeArray())
                        .delay(MAX_LOAD_MORE_FETCH_ITEMS_DELAY, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                new Consumer<ArrayList<Item>>() {
                                    @Override
                                    public void accept(ArrayList<Item> items) throws Exception {
                                        getViewListener().moreItemsLoaded(items);
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        getViewListener().onListItemsLoadMoreFail();
                                    }
                                }
                        )
        );
    }

    private ArrayList<Item> generateRandomThemeArray(){
        ArrayList<Item> items = new ArrayList<>();
        final int min = MIN_RANDOM_THEMES_TO_GENERATE;
        final int max = MAX_RANDOM_THEMES_TO_GENERATE;
        int amount = new Random().nextInt((max - min) + 1) + min;
        for (int i = 0; i < amount; i++) {
            items.add(new Item());
        }
        return items;
    }

}
