package com.kogimobile.baselibrary.sample.data.managers.remote;

import com.kogimobile.android.baselibrary.rest.BaseService;
import com.kogimobile.baselibrary.sample.data.repositories.user.source.RestUser;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Julian Cardona on 11/12/14.
 */
public class RestManagerPublicService extends BaseService<ApiServiceInterface> {

    public Single<RestUser> getUser(String userId){
        return getApiService()
                .getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
