package com.kogimobile.android.baselibrary.app.busevents;

import android.support.design.widget.Snackbar;
import android.view.View;

public class EventSnackbarMessage {

    public static final int NONE_VIEW = -1;

    private String message;
    private String action;
    private View.OnClickListener actionListener;
    private int viewId = NONE_VIEW;
    private Snackbar.Callback callback;

    public String getMessage() {
        return message;
    }

    public EventSnackbarMessage withMessage(String message){
        this.message = message;
        return this;
    }

    public String getAction() {
        return action;
    }

    public View.OnClickListener getActionListener() {
        return actionListener;
    }

    public EventSnackbarMessage withAction(String action, View.OnClickListener actionListener) {
        this.action = action;
        this.actionListener = actionListener;
        return this;
    }

    public Snackbar.Callback getCallback() {
        return callback;
    }

    public EventSnackbarMessage withCallback(Snackbar.Callback callback){
        this.callback = callback;
        return this;
    }

    public int getViewId() {
        return viewId;
    }

    public EventSnackbarMessage withViewId(int id){
        this.viewId = id;
        return this;
    }

}
