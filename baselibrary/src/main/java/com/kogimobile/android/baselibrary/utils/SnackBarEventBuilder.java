package com.kogimobile.android.baselibrary.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.kogimobile.android.baselibrary.R;
import com.kogimobile.android.baselibrary.app.busevents.EventSnackbarMessage;

/**
 * @author Pedro Scott pedro@kogimobile.com on 5/5/17.
 */

public class SnackBarEventBuilder {

    private EventSnackbarMessage event;
    private View viewRoot;

    public SnackBarEventBuilder(@NonNull EventSnackbarMessage event, @NonNull View view) {
        this.event = event;
        this.viewRoot = getViewRootToSnackBar(view);
    }

    public void showSnackBar() {
        getSnackBarByEvent().show();
    }

    private Snackbar getSnackBarByEvent() {
        Snackbar snackBar = Snackbar.make(viewRoot, getMessageEvent(), Snackbar.LENGTH_LONG);
        if (hasSnacBarAction()) {
            snackBar.setAction(getActionTextSnackBar(), event.getActionListener());
        }
        if (hasSnackBarCallBack()) {
            snackBar.setCallback(event.getCallback());
        }
        return snackBar;
    }

    private String getMessageEvent() {
        return event.getMessage() != null ? event.getMessage() : viewRoot.getContext().getString(event.getMessageId());
    }

    private boolean isCoordinatorView() {
        return viewRoot.findViewById(R.id.coordinator) != null;
    }

    private boolean isViewDiferentToNoneView(@NonNull EventSnackbarMessage event) {
        return event.getViewId() != EventSnackbarMessage.NONE_VIEW;
    }

    private boolean hasSnacBarAction() {
        return event.getActionListener() != null;
    }

    private boolean hasSnackBarCallBack() {
        return event.getCallback() != null;
    }

    private String getActionTextSnackBar() {
        return event.getActionText() != null ? getMessageEvent() : viewRoot.getContext().getString(event.getMessageId());
    }

    private View getViewRootToSnackBar(View view) {
        if (isViewDiferentToNoneView(event)) {
            viewRoot = view.findViewById(event.getViewId());
        } else if (isCoordinatorView()) {
            viewRoot = view.findViewById(R.id.coordinator);
        } else {
            viewRoot = view.findViewById(android.R.id.content);
        }
        return viewRoot;
    }


}
