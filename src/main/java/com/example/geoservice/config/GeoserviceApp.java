package com.example.geoservice.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class GeoserviceApp extends ResourceConfig {

    public GeoserviceApp() {
        register(new GeoserviceBinder());
        packages("com.example.geoservice.resource");
    }
}
