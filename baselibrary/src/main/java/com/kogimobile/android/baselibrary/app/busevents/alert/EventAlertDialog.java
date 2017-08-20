package com.kogimobile.android.baselibrary.app.busevents.alert;

import android.content.DialogInterface;
import android.support.annotation.StringRes;

/**
 * @author Julian Cardona on 7/11/14.
 */
public class EventAlertDialog {

    public static EventAlertDialog.Builder getBuilder() {
        return new EventAlertDialog.Builder();
    }

    @StringRes
    private int titleId;
    private String title = "";
    @StringRes
    private int messageId;
    private String message = "";
    private boolean cancellable = true;
    @StringRes
    private int positiveTextId;
    private String positiveButtonText;
    private DialogInterface.OnClickListener positiveListener;
    @StringRes
    private int negativeTextId;
    private String negativeButtonText;
    private DialogInterface.OnClickListener negativeListener;

    private EventAlertDialog(Builder builder) {
        this.titleId = builder.titleId;
        this.title = builder.title;
        this.messageId = builder.messageId;
        this.message = builder.message;
        this.cancellable = builder.cancellable;
        this.positiveTextId = builder.positiveTextId;
        this.positiveButtonText = builder.positiveButtonText;
        this.positiveListener = builder.positiveListener;
        this.negativeTextId = builder.negativeTextId;
        this.negativeButtonText = builder.negativeButtonText;
        this.negativeListener = builder.negativeListener;
    }

    public int getTitleId() {
        return titleId;
    }

    public String getTitle() {
        return title;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public int getPositiveTextId() {
        return positiveTextId;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public DialogInterface.OnClickListener getPositiveListener() {
        return positiveListener;
    }

    public int getNegativeTextId() {
        return negativeTextId;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public DialogInterface.OnClickListener getNegativeListener() {
        return negativeListener;
    }


    public static class Builder {
        @StringRes
        private int titleId;
        private String title = "";
        @StringRes
        private int messageId;
        private String message = "";
        private boolean cancellable = true;
        @StringRes
        private int positiveTextId;
        private String positiveButtonText;
        private DialogInterface.OnClickListener positiveListener;
        @StringRes
        private int negativeTextId;
        private String negativeButtonText;
        private DialogInterface.OnClickListener negativeListener;

        public Builder withTitleId(@StringRes int titleId) {
            this.titleId = titleId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withMesageId(@StringRes int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withPositiveButton(@StringRes int positiveTextId,DialogInterface.OnClickListener buttonListener) {
            this.positiveTextId = positiveTextId;
            this.positiveListener = buttonListener;
            return this;
        }

        public Builder withPositiveButton(String buttonText,DialogInterface.OnClickListener buttonListener) {
            this.positiveButtonText = buttonText;
            this.positiveListener = buttonListener;
            return this;
        }

        public Builder withNegativeButton(@StringRes int negativeTextId,DialogInterface.OnClickListener buttonListener) {
            this.negativeTextId = negativeTextId;
            this.negativeListener = buttonListener;
            return this;
        }

        public Builder withNegativeButton(String buttonText,DialogInterface.OnClickListener buttonListener) {
            this.negativeButtonText = buttonText;
            this.negativeListener = buttonListener;
            return this;
        }

        public EventAlertDialog build() {
            return new EventAlertDialog(this);
        }
    }
}