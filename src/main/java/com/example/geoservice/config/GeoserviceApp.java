package com.example.geoservice.config;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.example.geoservice.security.TokenSecurityRequestFilter;
import com.google.inject.Guice;

@ApplicationPath("rest")
public class GeoserviceApp extends ResourceConfig {

    @Inject
    public GeoserviceApp(ServiceLocator serviceLocator) {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        register(new TokenSecurityRequestFilter());
        register(new RolesAllowedDynamicFeature());
        packages("com.example.geoservice.resource");

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(Guice.createInjector(new GeoserviceBinder()));
    }
}
