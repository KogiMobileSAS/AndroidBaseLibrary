package com.kogimobile.android.baselibrary.rest.model.error;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Julian Cardona on 11/12/14.
 */
public class ApiError extends IllegalStateException implements Serializable{
    private static final long serialVersionUID = -4414321298872148245L;

    @Expose
    int code;
    @Expose
    String message;

    public ApiError(int code){
        this.code = code;
    }

    public int getErrorCode(){
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
