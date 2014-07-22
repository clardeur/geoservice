package com.example.geoservice.config;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.example.geoservice.security.TokenSecurityRequestFilter;

@ApplicationPath("rest")
public class GeoserviceApp extends ResourceConfig {

    @Inject
    public GeoserviceApp(ServiceLocator serviceLocator) {
        ServiceLocatorUtilities.enableImmediateScope(serviceLocator);
        register(new GeoserviceBinder());
        register(new TokenSecurityRequestFilter());
        register(new RolesAllowedDynamicFeature());
        packages("com.example.geoservice.resource");
    }
}
