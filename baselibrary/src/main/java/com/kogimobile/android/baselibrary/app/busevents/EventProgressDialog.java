package com.kogimobile.android.baselibrary.app.busevents;

/**
 * @author Julian Cardona on 7/11/14.
 */
public class EventProgressDialog {

    public static Builder getBuilder() {
        return new Builder();
    }

    private boolean show = true;
    private String progressDialogMessage = "";

    private EventProgressDialog(Builder builder){
        this.show = builder.show;
        this.progressDialogMessage = builder.message;
    }

    public boolean isShown() {
        return show;
    }

    public String getProgressDialogMessage() {
        return progressDialogMessage;
    }

    public static class Builder {
        private boolean show;
        private String message;

        public Builder withShow(boolean show) {
            this.show = show;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder dismiss(){
            this.show = false;
            return this;
        }

        public EventProgressDialog build() {
            return new EventProgressDialog(this);
        }
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    private EventProgressDialog(boolean show, String message){
        setShow(show);
        setProgressDialogMessage(message);
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventProgressDialog setShow(boolean show) {
        this.show = show;
        return this;
    }

    /**
     *
     * @deprecated Please use builder pattern
     */
    @Deprecated
    public EventProgressDialog setProgressDialogMessage(String progressDialogMessage) {
        this.progressDialogMessage = progressDialogMessage;
        return this;
    }

}