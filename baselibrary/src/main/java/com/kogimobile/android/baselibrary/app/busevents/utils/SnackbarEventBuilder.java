package com.kogimobile.android.baselibrary.app.busevents.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.kogimobile.android.baselibrary.R;
import com.kogimobile.android.baselibrary.app.busevents.EventSnackbarMessage;

/**
 * @author Pedro Scott pedro@kogimobile.com on 5/5/17.
 */

public class SnackbarEventBuilder {

    private EventSnackbarMessage event;
    private View viewRoot;

    public SnackbarEventBuilder(@NonNull EventSnackbarMessage event, @NonNull View view) {
        this.event = event;
        this.viewRoot = getViewRootToSnackbar(view);
    }

    public void showSnackBar() {
        getSnackbarByEvent().show();
    }

    private Snackbar getSnackbarByEvent() {
        Snackbar snackBar = Snackbar.make(viewRoot, getMessageEvent(), Snackbar.LENGTH_LONG);
        if (hasSnackbarAction()) {
            snackBar.setAction(getActionTextSnackbar(), event.getActionListener());
        }
        if (hasSnackbarCallBack()) {
            snackBar.setCallback(event.getCallback());
        }
        return snackBar;
    }

    private String getMessageEvent() {
        return event.getMessage() != null ? event.getMessage() : viewRoot.getContext().getString(event.getMessageId());
    }

    private boolean isCoordinatorView(View view) {
        return view.findViewById(R.id.coordinator) != null;
    }

    private boolean isViewDifferentToNoneView(@NonNull EventSnackbarMessage event) {
        return event.getViewId() != EventSnackbarMessage.NONE_VIEW;
    }

    private boolean hasSnackbarAction() {
        return event.getActionListener() != null;
    }

    private boolean hasSnackbarCallBack() {
        return event.getCallback() != null;
    }

    private String getActionTextSnackbar() {
        return event.getActionText() != null ? getMessageEvent() : viewRoot.getContext().getString(event.getMessageId());
    }

    private View getViewRootToSnackbar(View view) {
        if (isViewDifferentToNoneView(event)) {
            viewRoot = view.findViewById(event.getViewId());
        } else if (isCoordinatorView(view)) {
            viewRoot = view.findViewById(R.id.coordinator);
        } else {
            viewRoot = view.findViewById(android.R.id.content);
        }
        return viewRoot;
    }


}
