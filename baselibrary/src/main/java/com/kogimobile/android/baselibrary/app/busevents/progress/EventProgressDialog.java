package com.kogimobile.android.baselibrary.app.busevents.progress;

import android.support.annotation.StringRes;

/**
 * @author Julian Cardona on 7/11/14.
 */
public class EventProgressDialog {

    public static Builder getBuilder() {
        return new Builder();
    }

    @StringRes
    private int messageId;
    private String message = "";
    private boolean isCancelable = false;
    private boolean isDismiss = false;

    private EventProgressDialog(Builder builder){
        this.messageId = builder.messageId;
        this.message = builder.message;
        this.isCancelable = builder.isCancelable;
        this.isDismiss = builder.isDismiss;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public boolean isDismiss() {
        return isDismiss;
    }

    public static class Builder {

        @StringRes
        private int messageId;
        private String message;
        private boolean isDismiss = false;
        private boolean isCancelable = false;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withMessageId(@StringRes int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder dismiss(){
            this.isDismiss = true;
            return this;
        }

        public Builder withCancelable(boolean cancelable){
            this.isCancelable = cancelable;
            return this;
        }

        public EventProgressDialog build() {
            return new EventProgressDialog(this);
        }
    }

}