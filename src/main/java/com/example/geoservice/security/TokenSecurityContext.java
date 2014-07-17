package com.example.geoservice.security;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.util.Optional;

import com.example.geoservice.domain.Role;

public class TokenSecurityContext implements SecurityContext {

    public static final String AUTH_TOKEN_HEADER = "x-auth-token";

    public static final String AUTH_TOKEN_COOKIE = "AUTH_TOKEN";

    public static final String TOKEN_AUTH = "TOKEN";

    private SessionRepository repository;

    private String token = "";

    public TokenSecurityContext(ContainerRequestContext context, SessionRepository repository) {
        this.token = context.getHeaderString(AUTH_TOKEN_HEADER);
        this.repository = repository;
    }

    @Override
    public Principal getUserPrincipal() {
        Optional<Session> session = repository.get(token);

        return session.isPresent() ? session.get().user : null;
    }

    @Override
    public boolean isUserInRole(String role) {
        Optional<Session> session = repository.get(token);

        return session.isPresent() && session.get().user.getRoles().contains(Role.valueOf(role));
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return TOKEN_AUTH;
    }

}