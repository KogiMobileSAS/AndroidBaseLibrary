package com.kogimobile.android.baselibrary.app.busevents.progress;

/**
 * @author Julian Cardona on 7/11/14.
 */
public class EventProgressDialog {

    public static Builder getBuilder() {
        return new Builder();
    }

    private boolean show = true;
    private String progressDialogMessage = "";

    private EventProgressDialog(){}

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
        private boolean show = true;
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

}