package com.example.geoservice.security;

import static javax.ws.rs.Priorities.AUTHENTICATION;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(AUTHENTICATION)
public class TokenSecurityRequestFilter implements ContainerRequestFilter {

    @Inject
    private SessionRepository sessionRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        TokenSecurityContext securityContext = new TokenSecurityContext(requestContext, sessionRepository);
        requestContext.setSecurityContext(securityContext);
    }

}
