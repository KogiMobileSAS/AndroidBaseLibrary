package com.kogimobile.android.baselibrary.app.base;

import com.kogimobile.android.baselibrary.app.busevents.EventAlertDialog;
import com.kogimobile.android.baselibrary.app.busevents.EventProgressDialog;
import com.kogimobile.android.baselibrary.app.busevents.EventSnackbarMessage;
import com.kogimobile.android.baselibrary.app.busevents.EventToastMessage;

/**
 * Created by Julian Cardona on 9/22/16.
 */

public interface BaseEventBusListener {

    void onProgressDialogEvent(EventProgressDialog event);

    void onAlertDialogEvent(EventAlertDialog alert);

    void onToastMessageEvent(EventToastMessage event);

    void onSnackbarMessageEvent(EventSnackbarMessage event);

}
