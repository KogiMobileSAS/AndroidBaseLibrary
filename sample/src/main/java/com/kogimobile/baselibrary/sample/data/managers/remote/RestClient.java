package com.kogimobile.baselibrary.sample.data.managers.remote;

import android.support.annotation.NonNull;

import com.kogimobile.android.baselibrary.rest.ApiServiceFactory;
import com.kogimobile.android.baselibrary.rest.RestClientManager;
import com.kogimobile.android.baselibrary.rest.model.ServiceConfiguration;
import com.kogimobile.android.baselibrary.utils.StringUtils;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

/**
 * Created by Julian Cardona on 2/15/17.
 */

public class RestClient {

    private static RestClient instance = null;
    private static RestClientManager restClientManager;

    @NonNull
    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
            ServiceConfiguration<RestManagerPublicService, ApiServiceInterface> publicServiceConfiguration =
                    new ServiceConfiguration<>(
                            App.getGlobalContext().getString(R.string.base_url),
                            new ApiServiceFactory<>(RestManagerPublicService.class),
                            ApiServiceInterface.class);

            ServiceConfiguration<RestManagerPrivateService, ApiServiceInterface> privateServiceConfiguration =
                    new ServiceConfiguration<>(
                            App.getGlobalContext().getString(R.string.base_url),
                            new ApiServiceFactory<>(RestManagerPrivateService.class),
                            ApiServiceInterface.class);

            privateServiceConfiguration.setRequestInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request.Builder builder = (new Request.Builder()).url(request.url()).method(request.method(), request.body());
                    String accessToken = ""; // set access token
                    String tokenType = ""; // set token type

                    if (!StringUtils.isEmpty(tokenType) && !StringUtils.isEmpty(accessToken)) {
                        Header currentHeader = new Header("Authorization", String.format("%s %s", tokenType, accessToken));
                        builder.addHeader(currentHeader.name.utf8(), currentHeader.value.utf8());
                    }
                    return chain.proceed(builder.build());
                }
            });

            restClientManager = new RestClientManager();
            restClientManager.addServiceConfiguration(publicServiceConfiguration);
            restClientManager.addServiceConfiguration(privateServiceConfiguration);
        }
        return instance;
    }

    public static RestClientManager getRestClientManager() {
        return restClientManager;
    }
}
