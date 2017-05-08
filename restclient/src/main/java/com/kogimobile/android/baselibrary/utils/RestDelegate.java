package com.kogimobile.android.baselibrary.utils;

import com.kogimobile.android.baselibrary.restclient.R;

/**
 * @author kogiandroid on 12/24/15.
 */
public class RestDelegate {

    private static RestDelegate _delegate = null;

    public static RestDelegate getInstance() {
        if (_delegate == null) {
            _delegate = new RestDelegate();
        }
        return _delegate;
    }

    private int noInternetStringID = R.string.network_error_no_internet_connection;

    public int getNoInternetStringID() {
        return noInternetStringID;
    }

}
