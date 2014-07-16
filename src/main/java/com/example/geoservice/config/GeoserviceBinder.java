package com.example.geoservice.config;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.example.geoservice.repository.CityRepository;

public class GeoserviceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CityRepository.class).to(CityRepository.class).in(Singleton.class);
        bind(GeoserviceInterception.class).to(InterceptionService.class).in(Singleton.class);
    }
}
