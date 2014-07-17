package com.example.geoservice.security;

import static com.google.common.base.Preconditions.checkArgument;

import com.example.geoservice.domain.User;

public class Session {

    public final User user;

    public Session(User user) {
        checkArgument(user != null, "user is required");

        this.user = user;
    }

}
