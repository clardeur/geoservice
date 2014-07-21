package com.example.geoservice.config;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.example.geoservice.repository.CityRepository;
import com.example.geoservice.repository.Database;
import com.example.geoservice.security.SessionRepository;

public class GeoserviceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Persistence
        bind(Database.class).to(Database.class).in(Singleton.class);

        // Repository
        bind(CityRepository.class).to(CityRepository.class).in(Singleton.class);
        bind(SessionRepository.class).to(SessionRepository.class).in(Singleton.class);

        // Interceptors
        bind(GeoserviceInterception.class).to(InterceptionService.class).in(Singleton.class);
    }
}
