package com.example.geoservice.resource;

import com.example.geoservice.config.GeoserviceBinder;
import com.example.geoservice.domain.City;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class CityResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(CityResource.class).register(new GeoserviceBinder());
    }

    @Test
    public void should_get_city_by_id() throws Exception {
        Response response = target("/city/1").request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(City.class)).isEqualTo(new City(1L, "Paris"));
    }

    @Test
    public void should_return_not_found_status_code() throws Exception {
        Response response = target("/city/666").request().get();
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo("resource not found");
    }

    @Test
    public void should_return_not_found_when_id_is_invalid() throws Exception {
        assertThat(target("/city/BAD_ID").request().get().getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
        assertThat(target("/city/123_A").request().get().getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }
}
