package com.kogimobile.baselibrary.sample.data.managers.remote;

import com.kogimobile.android.baselibrary.rest.BaseService;
import com.kogimobile.baselibrary.sample.data.repositories.user.source.RestUser;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Julian Cardona on 14/04/15.
 */
public class RestManagerPrivateService extends BaseService<ApiServiceInterface> {

    public Single<RestUser> createUser(RestUser user){
        return getApiService()
                .createUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<RestUser> updateUser(RestUser user){
        return getApiService()
                .updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
