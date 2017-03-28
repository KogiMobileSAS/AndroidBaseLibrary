package com.kogimobile.android.baselibrary.app.busevents;

/**
 * @author Julian Cardona on 11/20/14.

 */
@Deprecated
public class EventToastMessage {

    private String message;

    public EventToastMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
