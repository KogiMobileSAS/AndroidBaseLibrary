package com.kogimobile.android.baselibrary.app.busevents;

import android.app.ProgressDialog;
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
public class EventProgressDialog {

    private boolean show = false;
    private String progressDialogMessage;
    private int progressDialogMessageResource = -1;
    private Spannable progressDialogMessageSpannable;

    /**
     * @param show    decide is the event show or hide the progress
     * @param message text to show in the progress
     */
    public EventProgressDialog(boolean show, String message) {
        setShow(show);
        setProgressDialogMessage(message);
    }

    /**
     * @param show    decide is the event show or hide the progress
     * @param message text to show in the progress
     */
    public EventProgressDialog(boolean show, int message) {
        setShow(show);
        setProgressDialogMessageResource(message);
    }

    /**
     * @param show    decide is the event show or hide the progress
     * @param message text to show in the progress
     */
    public EventProgressDialog(boolean show, Spannable message) {
        setShow(show);
        setProgressDialogMessageSpannable(message);
    }

    /**
     * @param show decide is the event show or hide the progress
     */
    public EventProgressDialog(boolean show) {
        setShow(show);
    }

    public boolean isShown() {
        return show;
    }

    public EventProgressDialog setShow(boolean show) {
        this.show = show;
        return this;
    }

    public String getProgressDialogMessage() {
        return progressDialogMessage;
    }

    public int getProgressDialogMessageResource() {
        return progressDialogMessageResource;
    }

    public Spannable getProgressDialogMessageSpannable() {
        return progressDialogMessageSpannable;
    }

    public void setProgressDialogMessageResource(int progressDialogMessageResource) {
        this.progressDialogMessageResource = progressDialogMessageResource;
    }

    public void setProgressDialogMessageSpannable(Spannable progressDialogMessageSpannable) {
        this.progressDialogMessageSpannable = progressDialogMessageSpannable;
    }

    public EventProgressDialog setProgressDialogMessage(String progressDialogMessage) {
        this.progressDialogMessage = progressDialogMessage;
        return this;
    }

    /**
     * This method manage the resource of the text to show in the progress and decide is the progress
     * need to show or hide.
     * Note: this methods have priorities in the text 1) resource, 2) String, 3) Spannable.
     *
     * @param progress is the view to show
     */
    public void manageProgress(ProgressDialog progress) {
        if (isShown()) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (getProgressDialogMessageResource() != -1) {
                progress.setMessage(progress.getContext().getString(getProgressDialogMessageResource()));
            } else if (getProgressDialogMessage() != null) {
                progress.setMessage(getProgressDialogMessage());
            } else if (getProgressDialogMessageSpannable() != null) {
                progress.setMessage(getProgressDialogMessageSpannable());
            } else {
                progress.setMessage("");
            }
            progress.show();
        } else {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }
    }

}