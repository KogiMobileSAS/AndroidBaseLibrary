package com.kogimobile.baselibrary.sample.data.repositories.user;

import com.kogimobile.baselibrary.sample.entities.User;

import io.reactivex.Single;

/**
 * Created by Julian Cardona on 1/25/17.
 */

public interface RepositoryUserDataSource {

    Single<User> get(String userId);

    Single<User> create(String id, String name);

    Single<User> update(String id, String name);

}
