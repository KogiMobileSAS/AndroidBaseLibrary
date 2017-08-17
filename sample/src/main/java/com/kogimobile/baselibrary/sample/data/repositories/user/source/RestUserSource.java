package com.kogimobile.baselibrary.sample.data.repositories.user.source;

import com.kogimobile.baselibrary.sample.data.managers.remote.RestClient;
import com.kogimobile.baselibrary.sample.data.managers.remote.RestManagerPrivateService;
import com.kogimobile.baselibrary.sample.data.managers.remote.RestManagerPublicService;
import com.kogimobile.baselibrary.sample.data.repositories.user.RepositoryUserDataSource;
import com.kogimobile.baselibrary.sample.entities.User;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by Julian Cardona on 2/15/17.
 */

public class RestUserSource implements RepositoryUserDataSource {

    private static RestUserSource instance;

    public static RestUserSource newInstance() {
        if (instance == null) {
            instance = new RestUserSource();
        }
        return instance;
    }

    @Override
    public Single<User> get(String userId) {
        return ((RestManagerPublicService) RestClient.getInstance().getRestClientManager().getService(RestManagerPublicService.class))
                .getUser(userId)
                .map(
                        new Function<RestUser, User>() {
                            @Override
                            public User apply(RestUser restUser) throws Exception {
                                return transformRestUserToEntity(restUser);
                            }
                        }
                );
    }

    @Override
    public Single<User> create(String id, String name) {
        return ((RestManagerPrivateService)RestClient.getInstance().getRestClientManager().getService(RestManagerPrivateService.class))
                .createUser(
                        new RestUser.Builder()
                                .withId(id)
                                .withName(name)
                                .build()
                )
                .map(
                        new Function<RestUser, User>() {
                            @Override
                            public User apply(RestUser restUser) throws Exception {
                                return transformRestUserToEntity(restUser);
                            }
                        }
                );
    }

    @Override
    public Single<User> update(String id, String name) {
        return ((RestManagerPrivateService)RestClient.getInstance().getRestClientManager().getService(RestManagerPrivateService.class))
                .updateUser(
                        new RestUser.Builder()
                                .withId(id)
                                .withName(name)
                                .build()
                )
                .map(
                        new Function<RestUser, User>() {
                            @Override
                            public User apply(RestUser restUser) throws Exception {
                                return transformRestUserToEntity(restUser);
                            }
                        }
                );
    }

    private User transformRestUserToEntity(RestUser restUser){
        return new RestUser.Builder()
                .withId(restUser.getId())
                .withName(restUser.getName())
                .build()
                .toEntity();
    }
}
