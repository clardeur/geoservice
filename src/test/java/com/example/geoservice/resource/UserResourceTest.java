package com.example.geoservice.resource;

import static com.example.geoservice.security.TokenSecurityContext.AUTH_TOKEN_COOKIE;
import static com.example.geoservice.security.TokenSecurityContext.AUTH_TOKEN_HEADER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.example.geoservice.domain.User;
import com.example.geoservice.security.SessionRepository;
import com.example.geoservice.security.TokenSecurityRequestFilter;
import com.example.geoservice.test.Provider;

public class UserResourceTest extends JerseyTest {

    SessionRepository sessionRepository;

    @Override
    protected Application configure() {
        this.sessionRepository = new SessionRepository();
        enable(TestProperties.DUMP_ENTITY);
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(UserResource.class)
                .register(new Provider<>(sessionRepository))
                .register(new TokenSecurityRequestFilter())
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

    @Test
    public void should_authenticate_an_user() throws Exception {
        // Given
        Form loginForm = new Form();
        loginForm.param("username", "foo");
        loginForm.param("password", "bar");

        // When
        Response response = target("/users/authenticate")
                .request()
                .buildPost(Entity.form(loginForm))
                .invoke();

        // Then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getCookies()).containsKey(AUTH_TOKEN_COOKIE);
        assertThat(response.getCookies().get(AUTH_TOKEN_COOKIE).getValue()).isNotEmpty();
    }

    @Test
    public void should_authenticate_and_get_current_user() throws Exception {
        // Given
        Form loginForm = new Form();
        loginForm.param("username", "foo");
        loginForm.param("password", "bar");

        Response authResponse = target("/users/authenticate")
                .request()
                .buildPost(Entity.form(loginForm))
                .invoke();

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
        Form loginForm = new Form();
        loginForm.param("username", "foo");
        loginForm.param("password", "bar");

        Response authResponse = target("/users/authenticate")
                .request()
                .buildPost(Entity.form(loginForm))
                .invoke();
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
        Form loginForm = new Form();
        loginForm.param("username", "foo");
        loginForm.param("password", "bar");

        Response authResponse = target("/users/authenticate")
                .request()
                .buildPost(Entity.form(loginForm))
                .invoke();
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

    @Test
    public void should_return_a_validation_error_when_username_is_empty() throws Exception {
        // Given
        Form loginForm = new Form();
        loginForm.param("username", "");
        loginForm.param("password", "bar");

        // When
        Response authResponse = target("/users/authenticate")
                .request(APPLICATION_JSON_TYPE)
                .buildPost(Entity.form(loginForm))
                .invoke();

        // Then
        assertThat(authResponse.getStatus()).isEqualTo(400);
    }

    @Test
    public void should_return_a_validation_error_when_password_is_empty() throws Exception {
        // Given
        Form loginForm = new Form();
        loginForm.param("username", "foo");
        loginForm.param("password", "");

        // When
        Response authResponse = target("/users/authenticate")
                .request(APPLICATION_JSON_TYPE)
                .buildPost(Entity.form(loginForm))
                .invoke();

        // Then
        assertThat(authResponse.getStatus()).isEqualTo(400);
    }
}
