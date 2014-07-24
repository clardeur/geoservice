package com.example.geoservice.resource;

import static com.example.geoservice.security.TokenSecurityContext.AUTH_TOKEN_COOKIE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.util.UUID;

import org.hibernate.validator.constraints.NotBlank;

import com.example.geoservice.domain.Role;
import com.example.geoservice.domain.User;
import com.example.geoservice.monitoring.Timed;
import com.example.geoservice.security.Session;
import com.example.geoservice.security.SessionRepository;

@Path("/users")
@Produces(APPLICATION_JSON)
public class UserResource {

    @Context
    private SecurityContext securityContext;

    private SessionRepository sessionRepository;

    @Inject
    public UserResource(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GET
    @Timed
    @Path("/current")
    public Response getCurrentUser() {
        return Response.ok(securityContext.getUserPrincipal()).build();
    }

    @POST
    @Path("/authenticate")
    public Response login(
            @NotBlank @FormParam("username") String username,
            @NotBlank @FormParam("password") String password) {
        String authToken = UUID.randomUUID().toString();

        // TODO: fetch from repository
        User user = new User(authToken);
        user.addRoles(Role.USER);

        sessionRepository.putIfAbsent(authToken, new Session(user));

        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE, authToken);

        return Response.ok("login success " + authToken)
                       .cookie(new NewCookie(cookie, "authentication token", 1800, true))
                       .build();
    }

    @GET
    @Path("/logout")
    public Response logout() {
        Principal principal = securityContext.getUserPrincipal();

        if (principal != null) {
            sessionRepository.remove(principal.getName());
        }

        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE, null);

        return Response.ok("logout success")
                       .cookie(new NewCookie(cookie, "deleted", 0, true))
                       .build();
    }

}