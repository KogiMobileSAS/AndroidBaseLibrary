package com.kogimobile.baselibrary.sample.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Julian Cardona on 2/2/15.
 */
public class User implements Parcelable{

    public static Builder getBuilder(){
        return new Builder();
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    private String id;
    private String name;

    public static class Builder {

        private String id;
        private String name;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
