package com.kogimobile.android.baselibrary.app.busevents;

import android.support.design.widget.Snackbar;
import android.view.View;

public class EventSnackbarMessage {

    public static final int NONE_VIEW = -1;

    public static EventSnackbarMessage.Builder getBuilder() {
        return new EventSnackbarMessage.Builder();
    }

    private String message;
    private String actionText;
    private int viewId = NONE_VIEW;
    private View.OnClickListener actionListener;
    private Snackbar.Callback callback;

    public EventSnackbarMessage() {}

    private EventSnackbarMessage(Builder builder) {
        this.message = builder.message;
        this.actionText = builder.actionText;
        this.viewId = builder.viewId;
        this.actionListener = builder.actionListener;
        this.callback = builder.callback;
    }

    public String getMessage() {
        return message;
    }

    public String getActionText() {
        return actionText;
    }

    public View.OnClickListener getActionListener() {
        return actionListener;
    }

    public Snackbar.Callback getCallback() {
        return callback;
    }

    public int getViewId() {
        return viewId;
    }

    public static class Builder {
        private String message;
        private String actionText;
        private int viewId = NONE_VIEW;
        private View.OnClickListener actionListener;
        private Snackbar.Callback callback;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withActionText(String actionText) {
            this.actionText = actionText;
            return this;
        }

        public Builder withViewId(int viewId) {
            this.viewId = viewId;
            return this;
        }

        public Builder withActionListener(View.OnClickListener actionListener) {
            this.actionListener = actionListener;
            return this;
        }

        public Builder withCallback(Snackbar.Callback callback) {
            this.callback = callback;
            return this;
        }

        public EventSnackbarMessage build() {
            return new EventSnackbarMessage(this);
        }
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withMessage(String message){
        this.message = message;
        return this;
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withAction(String action, View.OnClickListener actionListener) {
        this.actionText = action;
        this.actionListener = actionListener;
        return this;
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withCallback(Snackbar.Callback callback){
        this.callback = callback;
        return this;
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withViewId(int id){
        this.viewId = id;
        return this;
    }

}
