package com.example.geoservice.resource;

import static com.example.geoservice.security.TokenSecurityContext.AUTH_TOKEN_COOKIE;
import static com.example.geoservice.security.TokenSecurityContext.AUTH_TOKEN_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.example.geoservice.config.GeoserviceBinder;
import com.example.geoservice.domain.User;
import com.example.geoservice.security.TokenSecurityRequestFilter;

public class UserResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class)
                .register(new GeoserviceBinder())
                .register(new TokenSecurityRequestFilter());
    }

    @Test
    public void should_authenticate_an_user() throws Exception {
        Response response = target("/users/login").request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getCookies()).containsKey(AUTH_TOKEN_COOKIE);
        assertThat(response.getCookies().get(AUTH_TOKEN_COOKIE).getValue()).isNotEmpty();
    }

    @Test
    public void should_authenticate_and_get_current_user() throws Exception {
        // Given
        Response authResponse = target("/users/login").request().get();
        String authToken = authResponse.getCookies().get(AUTH_TOKEN_COOKIE).getValue();

        // When
        Response response = target("/users/current")
                .request()
                .header(AUTH_TOKEN_HEADER, authToken)
                .get();

        // Then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(User.class)).isEqualTo(new User(authToken));
    }

    @Test
    public void should_authenticate_then_logout() throws Exception {
        // Given
        Response authResponse = target("/users/login").request().get();
        String authToken = authResponse.getCookies().get(AUTH_TOKEN_COOKIE).getValue();

        // When
        Response logoutResponse = target("/users/logout")
                .request()
                .header(AUTH_TOKEN_HEADER, authToken)
                .get();

        // Then
        assertThat(logoutResponse.getStatus()).isEqualTo(200);
        assertThat(logoutResponse.getCookies()).containsKey(AUTH_TOKEN_COOKIE);
        assertThat(logoutResponse.getCookies().get(AUTH_TOKEN_COOKIE).getValue()).isEmpty();
        assertThat(logoutResponse.getCookies().get(AUTH_TOKEN_COOKIE).getMaxAge()).isZero();
    }

    @Test
    public void should_remove_user_session_when_logout() throws Exception {
        // Given
        Response authResponse = target("/users/login")
                .request()
                .get();
        String authToken = authResponse.getCookies().get(AUTH_TOKEN_COOKIE).getValue();

        target("/users/logout")
                .request()
                .header(AUTH_TOKEN_HEADER, authToken)
                .get();

        // When
        Response response = target("/users/current")
                .request()
                .header(AUTH_TOKEN_HEADER, authToken)
                .get();

        // Then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(User.class)).isNull();
    }
}
