package com.kogimobile.baselibrary.sample.data.repositories.user.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kogimobile.baselibrary.sample.entities.User;

/**
 * Created by Julian Cardona on 2/15/17.
 */

public class RestUser {

    public static Builder getBuilder(){
        return new Builder();
    }

    private RestUser(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User toEntity(){
        return User.getBuilder()
                .withId(getId())
                .withName(getName())
                .build();
    }

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

        public RestUser build() {
            return new RestUser(this);
        }
    }


}
