package com.kogimobile.android.baselibrary.rest.model.error;

import android.content.Context;

import com.google.gson.Gson;
import com.kogimobile.android.baselibrary.rest.utils.RestDelegate;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * @author Julian Cardona on 11/13/14.
 */
public class ApiErrorFactory {

    public static  void parse(Context ctx,Throwable error,ApiErrorListener listener) {
        if (isRelatedToNetwork(error)){
            listener.onNetworkError(ctx.getString(RestDelegate.getInstance().getNoInternetStringID()),getNetworkErrorType(error));
        }else if (error instanceof HttpException) {
            try {
                HttpException retroError = (HttpException) error;
                ApiError errorObj = new Gson().fromJson(retroError.response().errorBody().charStream(), ApiError.class);
                listener.onApiError(ErrorFactory.getErrorMessage(ctx,errorObj.getErrorCode()),errorObj);
            } catch (Throwable e) {
                e.printStackTrace();
                listener.onNetworkError(ErrorFactory.getErrorMessage(ctx,ErrorFactory.GENERAL_DEFAULT_ERROR),NetworkErrorType.GENERAL);
            }
        }
        else if(error instanceof ApiError){
            listener.onApiError(ErrorFactory.getErrorMessage(ctx,((ApiError) error).getErrorCode()), (ApiError) error);
        }
    }

    public static NetworkErrorType getNetworkErrorType(Throwable error) {
        if (isNetworkError(error)){
            return NetworkErrorType.NETWORK;
        }else if (isUnknownHostException(error)){
            return NetworkErrorType.UNKNOWN_HOST;
        }else if (isSocketTimeoutException(error)){
            return NetworkErrorType.SOCKET_TIME_OUT;
        }

        return NetworkErrorType.GENERAL;
    }

    private static boolean isNetworkError(Throwable error) {
        return error instanceof java.net.ConnectException;
    }

    private static boolean isUnknownHostException(Throwable error) {
        return error instanceof UnknownHostException;
    }

    private static boolean isSocketTimeoutException(Throwable error) {
        return error instanceof SocketTimeoutException;
    }

    public static boolean isRelatedToNetwork(Throwable error) {
        return isNetworkError(error) || isSocketTimeoutException(error) || isUnknownHostException(error);
    }
}
