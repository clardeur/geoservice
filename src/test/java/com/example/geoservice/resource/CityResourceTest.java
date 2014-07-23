package com.example.geoservice.resource;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import com.example.geoservice.domain.City;
import com.example.geoservice.repository.CityRepository;
import com.example.geoservice.repository.Database;
import com.example.geoservice.test.Provider;

public class CityResourceTest extends JerseyTest {

    private static Database db;
    private static CityRepository repository;

    @BeforeClass
    public static void setUpStatic() {
        db = new Database();
        repository = new CityRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        try (Handle handle = db.getConnection().open()) {
            handle.execute("truncate table city");
        }
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(CityResource.class)
                .register(new Provider<>(repository));
    }

    @Test
    public void should_get_city_by_id() throws Exception {
        Long id = repository.insert(new City());
        Response response = target("/city/" + id).request().get();
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

    @Test
    public void should_create_a_new_city() throws Exception {
        City city = new City();
        city.setName("Paris");
        Response post = target("/city").request().post(Entity.json(city));
        assertThat(post.getStatus()).isEqualTo(CREATED.getStatusCode());
    }
}
