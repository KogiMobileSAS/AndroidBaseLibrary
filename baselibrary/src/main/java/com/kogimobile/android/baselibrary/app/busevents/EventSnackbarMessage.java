package com.kogimobile.android.baselibrary.app.busevents;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

public class EventSnackbarMessage {

    public static final int NONE_VIEW = -1;

    public static EventSnackbarMessage.Builder getBuilder() {
        return new EventSnackbarMessage.Builder();
    }

    private String message;
    @StringRes
    private int messageId;
    private String actionText;
    @StringRes
    private int actionTextId;
    private int viewId = NONE_VIEW;
    private View.OnClickListener actionListener;
    private Snackbar.Callback callback;

    public EventSnackbarMessage() {
    }

    private EventSnackbarMessage(Builder builder) {
        this.message = builder.message;
        this.messageId = builder.messageId;
        this.actionText = builder.actionText;
        this.actionTextId = builder.actionTextId;
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

    public int getMessageId() {
        return messageId;
    }

    public int getActionTextId() {
        return actionTextId;
    }

    public static class Builder {

        private String message;
        @StringRes
        private int messageId;
        private String actionText;
        @StringRes
        private int actionTextId;
        private int viewId = NONE_VIEW;
        private View.OnClickListener actionListener;
        private Snackbar.Callback callback;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withMessage(@StringRes int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder withActionText(String actionText) {
            this.actionText = actionText;
            return this;
        }

        public Builder withActionText(@StringRes int actionTextId) {
            this.actionTextId = actionTextId;
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
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withAction(String action, View.OnClickListener actionListener) {
        this.actionText = action;
        this.actionListener = actionListener;
        return this;
    }

    /**
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withCallback(Snackbar.Callback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventSnackbarMessage withViewId(int id) {
        this.viewId = id;
        return this;
    }

}
