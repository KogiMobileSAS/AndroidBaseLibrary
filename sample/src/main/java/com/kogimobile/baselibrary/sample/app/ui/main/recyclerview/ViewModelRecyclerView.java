package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.kogimobile.baselibrary.sample.entities.Item;

import java.util.ArrayList;
import java.util.List;
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

    public Single<Pair<List<Item>, Boolean>> getListItems(){
        return Single
                .just(generateRandomThemeArray())
                .delay(MAX_LOAD_FETCH_ITEMS_DELAY, TimeUnit.SECONDS)
                .flatMap(getMapperFunc(true))
                .observeOn(AndroidSchedulers.mainThread());
    }



    public  Single<Pair<List<Item>, Boolean>> getMoreListItems(){
        return Single
                .just(generateRandomThemeArray())
                .delay(MAX_LOAD_MORE_FETCH_ITEMS_DELAY, TimeUnit.SECONDS)
                .flatMap(getMapperFunc(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    private Function<List<Item>, Single<Pair<List<Item>, Boolean>>> getMapperFunc(final boolean hasMoreToload) {
        return new Function<List<Item>, Single<Pair<List<Item>,Boolean>>>() {
            @Override
            public Single<Pair<List<Item>,Boolean>> apply(List<Item> items) throws Exception {
                return Single.just(new Pair<>(items,hasMoreToload));
            }
        };
    }

    private List<Item> generateRandomThemeArray(){
        List<Item> items = new ArrayList<>();
        final int min = MIN_RANDOM_THEMES_TO_GENERATE;
        final int max = MAX_RANDOM_THEMES_TO_GENERATE;
        int amount = new Random().nextInt((max - min) + 1) + min;
        for (int i = 0; i < amount; i++) {
            items.add(new Item());
        }
        return items;
    }

}
