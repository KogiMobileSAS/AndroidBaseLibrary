package com.kogimobile.baselibrary.sample.data;

import com.kogimobile.baselibrary.sample.data.repositories.user.RepositoryUser;

/**
 * Created by Julian Cardona on 2/15/17.
 */

public class Injection {

    public static RepositoryUser getUserRepository() {
        return RepositoryUser.newInstance();
    }

}
