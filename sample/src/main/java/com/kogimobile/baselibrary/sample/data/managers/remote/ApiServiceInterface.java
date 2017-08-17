package com.kogimobile.baselibrary.sample.data.managers.remote;

import com.kogimobile.baselibrary.sample.data.repositories.user.source.RestUser;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Julian Cardona on 11/7/14.
 */
public interface ApiServiceInterface {

    @POST("users")
    Single<RestUser> createUser(@Body RestUser user);

    @PATCH("users")
    Single<RestUser> updateUser(@Body RestUser request);

    @GET("users/{user_id}")
    Single<RestUser> getUser(@Path("user_id") String userId);

}
