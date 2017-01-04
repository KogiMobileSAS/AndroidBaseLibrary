package com.kogimobile.android.baselibrary.app.busevents;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;

/**
 * @author Julian Cardona. julian@kogimobile.com
 * @version 9/4/16. *
 *          Copyright 2015 Kogi Mobile
 *          <p>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p>
 *          http://www.apache.org/licenses/LICENSE-2.0
 *          <p>
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 * @modified Pedro Scott. scott7462@gmail.com
 */
public class EventAlertDialog {

    //String Values
    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;

    //Resource Values
    private int titleResource = -1;
    private int messageResource = -1;
    private int positiveButtonTextResource = -1;
    private int negativeButtonTextResource = -1;

    //Spannable Resource
    private Spannable titleSpannable;
    private Spannable messageSpannable;
    private Spannable positiveButtonTextSpannable;
    private Spannable negativeButtonTextSpannable;

    //Listeners Resource
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;
    private boolean cancellable = true;


    /**
     * @return String that you are using in title of the alert
     */
    private String getTitle() {
        return title;
    }

    /**
     * @return the resource of R.string file
     */
    private int getTitleResource() {
        return titleResource;
    }

    /**
     * @return the Spannable that you are using in title of the alert
     */
    private Spannable getTitleSpannable() {
        return titleSpannable;
    }

    /**
     * @param title is the title to display on the dialog
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @param title is the title to display on the dialog
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withTitle(int title) {
        this.titleResource = title;
        return this;
    }

    /**
     * @param title is the title to display on the dialog
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withTitle(Spannable title) {
        this.titleSpannable = title;
        return this;
    }

    /**
     * @param message is the message to display on the dialog
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withMessage(int message) {
        this.messageResource = message;
        return this;
    }

    /**
     * @param message is the message to display on the dialog
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @param message is the message to display on the dialog
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withMessage(Spannable message) {
        this.messageSpannable = message;
        return this;
    }

    private int getMessageResource() {
        return messageResource;
    }

    private String getMessage() {
        return message;
    }

    private Spannable getMessageSpannable() {
        return messageSpannable;
    }

    /**
     * @return the value to notify if the dialog can by cancelable or not.
     */
    public boolean isCancellable() {
        return cancellable;
    }

    /**
     * @param cancellable is the value to notify if the dialog can by cancelable or not.
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog cancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }

    //Listener Options
    private DialogInterface.OnClickListener getPositiveListener() {
        return positiveListener;
    }

    private DialogInterface.OnClickListener getNegativeListener() {
        return negativeListener;
    }

    /**
     * @param title    is the text to set in the positive button
     * @param listener the action to do is you do click on the positive button
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withPositiveButton(int title, DialogInterface.OnClickListener listener) {
        this.positiveButtonTextResource = title;
        this.positiveListener = listener;
        return this;
    }

    /**
     * @param title    is the text to set in the positive button
     * @param listener the action to do is you do click on the positive button
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withPositiveButton(String title, DialogInterface.OnClickListener listener) {
        this.positiveButtonText = title;
        this.positiveListener = listener;
        return this;
    }

    /**
     * @param title    is the text to set in the positive button
     * @param listener the action to do is you do click on the positive button
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withPositiveButton(Spannable title, DialogInterface.OnClickListener listener) {
        this.positiveButtonTextSpannable = title;
        this.positiveListener = listener;
        return this;
    }

    private int getPositiveButtonTextResource() {
        return positiveButtonTextResource;
    }

    private String getPositiveButtonText() {
        return positiveButtonText;
    }

    private Spannable getPositiveButtonTextSpannable() {
        return positiveButtonTextSpannable;
    }

    /**
     * @param title    is the text to set in the negative button
     * @param listener the action to do is you do click on the negative button
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withNegativeButton(int title, DialogInterface.OnClickListener listener) {
        this.negativeButtonTextResource = title;
        this.negativeListener = listener;
        return this;
    }

    /**
     * @param title    is the text to set in the negative button
     * @param listener the action to do is you do click on the negative button
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withNegativeButton(String title, DialogInterface.OnClickListener listener) {
        this.negativeButtonText = title;
        this.negativeListener = listener;
        return this;
    }

    /**
     * @param title    is the text to set in the negative button
     * @param listener the action to do is you do click on the negative button
     * @return the instance of the event to work like a builder
     */
    public EventAlertDialog withNegativeButton(Spannable title, DialogInterface.OnClickListener listener) {
        this.negativeButtonTextSpannable = title;
        this.negativeListener = listener;
        return this;
    }

    private int getNegativeButtonTextResource() {
        return negativeButtonTextResource;
    }

    private String getNegativeButtonText() {
        return negativeButtonText;
    }

    private Spannable getNegativeButtonTextSpannable() {
        return negativeButtonTextSpannable;
    }

    /**
     * This method is the delegate to manage the title text by difference source of text
     * like resource, strings or Spannable
     * Note: this methods have priorities in the text 1) resource, 2) String, 3) Spannable.
     *
     * @param builder is the builder dialog to manage the title to show
     * @return the builder dialog
     */
    public AlertDialog.Builder setAlertDialogTitle(AlertDialog.Builder builder) {
        if (getTitleResource() != -1) {
            builder.setTitle(getTitleResource());
        } else if (getTitle() != null) {
            builder.setTitle(getTitle());
        } else if (getTitleSpannable() != null) {
            builder.setTitle(getTitleSpannable());
        } else {
            builder.setTitle("");
        }
        return builder;
    }

    /**
     * This method is the delegate to manage the message text by difference source of text
     * like resource, strings or Spannable
     * Note: this methods have priorities in the text 1) resource, 2) String, 3) Spannable.
     *
     * @param builder is the builder dialog to manage the message to show
     * @return the builder dialog
     */
    public AlertDialog.Builder setAlertDialogMessage(AlertDialog.Builder builder) {
        if (getMessageResource() != -1) {
            builder.setMessage(getMessageResource());
        } else if (getMessage() != null) {
            builder.setMessage(getMessage());
        } else if (getMessageSpannable() != null) {
            builder.setMessage(getMessageSpannable());
        } else {
            builder.setMessage("");
        }
        return builder;
    }

    /**
     * This method is the delegate to manage the positive button text by difference source of text
     * like resource, strings or Spannable and set the listener to the button
     * Note: this methods have priorities in the text 1) resource, 2) String, 3) Spannable.
     *
     * @param builder is the builder dialog to manage the positive button
     * @return the builder dialog
     */
    public AlertDialog.Builder setAlertDialogPositiveButton(AlertDialog.Builder builder) {
        if (getPositiveButtonTextResource() != -1) {
            builder.setPositiveButton(getPositiveButtonTextResource(),
                    getPositiveButtonListener());
        } else if (getPositiveButtonText() != null) {
            builder.setPositiveButton(getPositiveButtonText(),
                    getPositiveButtonListener());
        } else if (getPositiveButtonTextSpannable() != null) {
            builder.setPositiveButton(getPositiveButtonTextSpannable(),
                    getPositiveButtonListener());
        } else {
            builder.setPositiveButton(android.R.string.ok,
                    getPositiveButtonListener());
        }
        return builder;
    }

    /**
     * This method is the delegate to manage the negative button text by difference source of text
     * like resource, strings or Spannable and set the listener to the button
     * Note: this methods have priorities in the text 1) resource, 2) String, 3) Spannable.
     *
     * @param builder is the builder dialog to manage the negative button
     * @return the builder dialog
     */
    public AlertDialog.Builder setAlertDialogNegativeButton(AlertDialog.Builder builder) {
        if (getNegativeButtonListener() != null) {
            if (getNegativeButtonTextResource() != -1) {
                builder.setNegativeButton(getNegativeButtonTextResource(),
                        getNegativeButtonListener());
            } else if (getNegativeButtonText() != null) {
                builder.setNegativeButton(getNegativeButtonText(),
                        getNegativeButtonListener());
            } else if (getNegativeButtonTextSpannable() != null) {
                builder.setNegativeButton(getNegativeButtonTextSpannable(),
                        getNegativeButtonListener());
            } else {
                builder.setNegativeButton(android.R.string.cancel,
                        getNegativeButtonListener());
            }
        }
        return builder;
    }

    private DialogInterface.OnClickListener getPositiveButtonListener() {
        return getPositiveListener() == null ? new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        } : getPositiveListener();
    }

    private DialogInterface.OnClickListener getNegativeButtonListener() {
        return getNegativeListener() == null ? new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        } : getNegativeListener();
    }
}