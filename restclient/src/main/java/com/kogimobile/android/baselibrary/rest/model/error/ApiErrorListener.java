package com.kogimobile.android.baselibrary.rest.model.error;

/**
 * @author Julian Cardona on 8/8/16.
 */
public interface ApiErrorListener {

    void onNetworkError(String errorMessage,NetworkErrorType type);

    void onApiError(String errorMessage,ApiError error);

}
