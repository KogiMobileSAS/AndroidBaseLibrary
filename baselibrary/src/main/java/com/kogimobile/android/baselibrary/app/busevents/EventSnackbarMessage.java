package com.kogimobile.android.baselibrary.app.busevents;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.view.View;

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
public class EventSnackbarMessage {

    public static final int NONE_VIEW = -1;

    private String message;
    private int messageResource;
    private Spannable messageSpannable;
    private String action;
    private View.OnClickListener actionListener;
    private int viewId = NONE_VIEW;
    private Snackbar.Callback callback;

    public String getMessage() {
        return message;
    }

    public int getMessageResource() {
        return messageResource;
    }

    public Spannable getMessageSpannable() {
        return messageSpannable;
    }

    /**
     * @param message text to show in the SnackBar
     */
    public EventSnackbarMessage withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @param message text to show in the SnackBar
     */
    public EventSnackbarMessage withMessage(int message) {
        this.messageResource = message;
        return this;
    }

    /**
     * @param message text to show in the SnackBar
     */
    public EventSnackbarMessage withMessage(Spannable message) {
        this.messageSpannable = message;
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

    public EventSnackbarMessage withCallback(Snackbar.Callback callback) {
        this.callback = callback;
        return this;
    }

    public int getViewId() {
        return viewId;
    }

    public EventSnackbarMessage withViewId(int id) {
        this.viewId = id;
        return this;
    }

    /**
     * This method is the delegate to manage the negative button text by difference source of text
     * like resource, strings or Spannable and set the listener to the button
     * Note: this methods have priorities in the text 1) resource, 2) String, 3) Spannable.
     *
     * @param root is the view where the is showing
     * @return the SnackBar
     */
    public Snackbar managerSnackBar(View root, int lengthLong) {
        if (getMessageResource() != -1) {
            return Snackbar.make(root, getMessageResource(), lengthLong);
        } else if (getMessage() != null) {
            return Snackbar.make(root, getMessage(), lengthLong);
        } else if (getMessageSpannable() != null) {
            return Snackbar.make(root, getMessageSpannable(), lengthLong);
        } else {
            return Snackbar.make(root, "", lengthLong);
        }
    }
}
