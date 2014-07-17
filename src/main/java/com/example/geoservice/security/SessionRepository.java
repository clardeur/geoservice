package com.example.geoservice.security;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionRepository {

    private Map<String, Session> sessions = new HashMap<>();

    public Optional<Session> get(String key) {
        return Optional.ofNullable(sessions.get(key));
    }

    public Session putIfAbsent(String key, Session session) {
        checkArgument(session != null, "session is required");
        checkArgument(key != null, "key is required");

        return sessions.putIfAbsent(key, session);
    }

    public void remove(String key) {
        sessions.remove(key);
    }

}
