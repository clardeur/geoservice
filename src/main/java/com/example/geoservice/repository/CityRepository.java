package com.example.geoservice.repository;

import java.util.Map;
import java.util.Optional;

import com.example.geoservice.domain.City;
import com.example.geoservice.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableMap;

public class CityRepository {

    private Map<Long, City> cities = ImmutableMap.<Long, City>builder()
                                         .put(1L, new City(1L, "Paris"))
                                         .put(2L, new City(2L, "Lille"))
                                         .put(3L, new City(3L, "Montpellier"))
                                         .build();

    public City findById(Long id) {
        return Optional.ofNullable(cities.get(id))
                       .orElseThrow(ResourceNotFoundException::new);
    }
}
