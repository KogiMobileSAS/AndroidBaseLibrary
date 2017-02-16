package com.kogimobile.android.baselibrary.rest;

/**
 * @author kogiandroid on 1/14/16.
 */
public interface Factory<T> {
    T factory() throws IllegalAccessException, InstantiationException;
}
