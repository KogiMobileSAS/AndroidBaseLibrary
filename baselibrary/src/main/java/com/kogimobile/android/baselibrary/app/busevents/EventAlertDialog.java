package com.kogimobile.android.baselibrary.app.busevents;

import android.content.DialogInterface;

/**
 * Created by Julian Cardona on 7/11/14.
 */
public class EventAlertDialog {

    private String title = "";
    private String message = "";
    private boolean cancellable = true;
    private String positiveButtonText;
    private DialogInterface.OnClickListener positiveListener;
    private String negativeButtonText;
    private DialogInterface.OnClickListener negativeListener;

    public String getTitle() {
        return title;
    }

    public EventAlertDialog withTitle(String title) {
        this.title = title;
        return this;

    }

    public String getMessage() {
        return message;
    }

    public EventAlertDialog withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public DialogInterface.OnClickListener getPositiveListener() {
        return positiveListener;
    }

    public EventAlertDialog withPositiveButton(String title,DialogInterface.OnClickListener listener){
        this.positiveButtonText = title;
        this.positiveListener = listener;
        return this;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public DialogInterface.OnClickListener getNegativeListener() {
        return negativeListener;
    }

    public EventAlertDialog withNegativeButton(String title,DialogInterface.OnClickListener listener){
        this.negativeButtonText = title;
        this.negativeListener = listener;
        return this;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public EventAlertDialog cancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }
}