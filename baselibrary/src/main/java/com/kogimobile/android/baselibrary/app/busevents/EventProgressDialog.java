package com.kogimobile.android.baselibrary.app.busevents;

/**
 * @author Julian Cardona on 7/11/14.
 */
public class EventProgressDialog {

    private boolean show = false;
    private String progressDialogMessage = "";

    public EventProgressDialog(boolean show, String message){
        setShow(show);
        setProgressDialogMessage(message);
    }

    public EventProgressDialog(boolean show){
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

    public EventProgressDialog setProgressDialogMessage(String progressDialogMessage) {
        this.progressDialogMessage = progressDialogMessage;
        return this;
    }

}