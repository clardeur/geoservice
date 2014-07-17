package com.example.geoservice.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.example.geoservice.security.TokenSecurityRequestFilter;

@ApplicationPath("rest")
public class GeoserviceApp extends ResourceConfig {

    public GeoserviceApp() {
        register(new GeoserviceBinder());
        register(new TokenSecurityRequestFilter());
        register(new RolesAllowedDynamicFeature());
        packages("com.example.geoservice.resource");
    }
}
