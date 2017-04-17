package com.kogimobile.android.baselibrary.rest;

/**
 * @author kogiandroid on 1/14/16.
 */
public class BaseService<I> {

    public I getApiServiceInterface() {
        return apiServiceInterface;
    }

    void setApiServiceInterface(I apiServiceInterface) {
        this.apiServiceInterface = apiServiceInterface;
    }

    private I apiServiceInterface;

    /**
     * @deprecated use @see com.kogimobile.androidstudiobaselib.rest.ApiServiceFactory
     * @param apiServiceInterface
     */
    @Deprecated
    public BaseService(I apiServiceInterface) {
        this.apiServiceInterface = apiServiceInterface;
    }

    public BaseService(){

    }

    public I getApiService() {
        return apiServiceInterface;
    }

}
