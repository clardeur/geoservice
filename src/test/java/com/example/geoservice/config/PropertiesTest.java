package com.example.geoservice.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PropertiesTest {

    private Properties properties;

    @Before
    public void setUp() throws Exception {
        properties = new Properties();
    }

    @Test
    public void should_load_properties() throws Exception {
        assertThat(properties).isNotEmpty();
    }
}
