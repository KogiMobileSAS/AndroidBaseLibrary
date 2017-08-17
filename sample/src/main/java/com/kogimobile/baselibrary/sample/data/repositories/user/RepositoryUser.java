package com.kogimobile.baselibrary.sample.data.repositories.user;

import android.support.annotation.NonNull;

import com.kogimobile.baselibrary.sample.data.repositories.user.source.RestUserSource;
import com.kogimobile.baselibrary.sample.entities.User;

import io.reactivex.Single;

/**
 * Created by Julian Cardona on 2/15/17.
 */

public class RepositoryUser implements RepositoryUserDataSource {

    private static RepositoryUser instance = null;
    private static RestUserSource sourceRest;

    @NonNull
    public static RepositoryUser newInstance() {
        if (instance == null) {
            instance = new RepositoryUser();
        }
        return instance;
    }

    @Override
    public Single<User> get(String userId) {
        return getRestSource().get(userId);
    }

    @Override
    public Single<User> create(String id, String name) {
        return  getRestSource().create(id,name);
    }

    @Override
    public Single<User> update(String id, String name) {
        return  getRestSource().update(id,name);
    }

    private RestUserSource getRestSource(){
        if(sourceRest == null){
            sourceRest = RestUserSource.newInstance();
        }
        return sourceRest;
    }

}
