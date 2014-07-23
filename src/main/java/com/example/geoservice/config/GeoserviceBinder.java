package com.example.geoservice.config;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InterceptionService;

import com.example.geoservice.monitoring.DatabaseHealthCheck;
import com.example.geoservice.repository.CityRepository;
import com.example.geoservice.repository.Database;
import com.example.geoservice.security.SessionRepository;
import com.google.inject.servlet.ServletModule;

public class GeoserviceBinder extends ServletModule {

    @Override
    protected void configureServlets() {
        // Persistence
        bind(Database.class).in(Singleton.class);

        // Repository
        bind(CityRepository.class).in(Singleton.class);
        bind(SessionRepository.class).in(Singleton.class);

        // Interceptors
        bind(InterceptionService.class).to(GeoserviceInterception.class).in(Singleton.class);

        // Monitoring
        bind(DatabaseHealthCheck.class).asEagerSingleton();
    }
}
