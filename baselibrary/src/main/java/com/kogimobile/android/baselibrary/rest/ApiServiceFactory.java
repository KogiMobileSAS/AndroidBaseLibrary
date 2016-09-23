package com.kogimobile.android.baselibrary.rest;

/**
 * Created by kogiandroid on 1/14/16.
 */
public class ApiServiceFactory<T> implements Factory<T> {

    private final Class<T> serviceClass;

    public ApiServiceFactory(Class<T> serviceClass){
        this.serviceClass = serviceClass;
    }

    @Override
    public T factory() throws IllegalAccessException, InstantiationException {
        return serviceClass.newInstance();
    }

}
