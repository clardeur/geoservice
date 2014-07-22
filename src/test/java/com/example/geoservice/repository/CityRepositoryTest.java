package com.example.geoservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import com.example.geoservice.domain.City;

public class CityRepositoryTest {

    private static Database db;
    private static CityRepository repository;

    @BeforeClass
    public static void setUpStatic() {
        db = new Database();
        repository = new CityRepository(db);
    }

    @After
    public void tearDown() {
        try (Handle handle = db.getConnection().open()) {
            handle.execute("truncate table city");
        }
    }
    @AfterClass
    public static void tearDownStatic() throws Exception {
        try (Handle handle = db.getConnection().open()) {
            handle.execute("drop table city");
        }
    }

    @Test
    public void should_insert_a_city() throws Exception {
        // Given
        City newCity = new City();
        newCity.setName("Paris");

        // When
        repository.insert(newCity);

        // Then
        City insertedCity = repository.findById(newCity.getId());
        assertThat(newCity).isEqualTo(insertedCity);
    }

    @Test
    public void should_update_a_city() throws Exception {
        // Given
        City city = new City();
        city.setName("Paris");

        repository.insert(city);
        city.setName("London");

        // When
        repository.update(city);

        // Then
        City updatedCity = repository.findById(city.getId());
        assertThat(updatedCity.getName()).isEqualTo("London");
    }

    @Test
    public void should_delete_a_city() throws Exception {
        // Given
        City city = new City();
        city.setName("Paris");

        repository.insert(city);

        // When
        repository.delete(city.getId());

        // Then
        City deletedCity = repository.findById(city.getId());
        assertThat(deletedCity).isNull();
    }
}
