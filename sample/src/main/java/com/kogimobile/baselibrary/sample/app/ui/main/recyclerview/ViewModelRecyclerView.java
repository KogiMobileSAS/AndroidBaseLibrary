package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.arch.lifecycle.ViewModel;

import com.kogimobile.baselibrary.sample.entities.Item;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by juliancardona on 3/11/17.
 */

public class ViewModelRecyclerView extends ViewModel {

    private final int MIN_RANDOM_THEMES_TO_GENERATE = 10;
    private final int MAX_RANDOM_THEMES_TO_GENERATE = 20;
    private final int MAX_LOAD_FETCH_ITEMS_DELAY = 1;//Seconds
    private final int MAX_LOAD_MORE_FETCH_ITEMS_DELAY = 2;//Seconds

    private boolean hasMoreToLoad;

    public boolean hasMoreToLoad() {
        return hasMoreToLoad;
    }

    public Single<ArrayList<Item>> getListItems(){
        return Single
                .just(generateRandomThemeArray())
                .delay(MAX_LOAD_FETCH_ITEMS_DELAY, TimeUnit.SECONDS)
                .flatMap(
                        new Function<ArrayList<Item>, Single<ArrayList<Item>>>() {
                            @Override
                            public Single<ArrayList<Item>> apply(ArrayList<Item> items) throws Exception {
                                hasMoreToLoad = true;
                                return Single.just(items);
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ArrayList<Item>> getMoreListItems(){
        return Single
                .just(generateRandomThemeArray())
                .delay(MAX_LOAD_MORE_FETCH_ITEMS_DELAY, TimeUnit.SECONDS)
                .flatMap(
                        new Function<ArrayList<Item>, Single<ArrayList<Item>>>() {
                            @Override
                            public Single<ArrayList<Item>> apply(ArrayList<Item> items) throws Exception {
                                hasMoreToLoad = false;
                                return Single.just(items);
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
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
