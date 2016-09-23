package com.kogimobile.android.baselibrary.rest.model.error;

/**
 * Created by Julian Cardona on 8/8/16.
 */
public interface ApiErrorListener {

    public void onNetworkError(String errorMessage,NetworkErrorType type);

    public void onApiError(String errorMessage,ApiError error);

}
