package com.example.geoservice.repository;

import javax.inject.Inject;

import com.example.geoservice.domain.City;

public class CityRepository {

    private Database database;

    @Inject
    public CityRepository(Database database) {
        this.database = database;
        createTableIfNotExists();
    }

    public Long insert(City city) {
        return database.getConnection().withHandle(
                handle -> handle.createStatement("insert into city(name) values (:name)")
                                .bind("name", city.getName())
                                .executeAndReturnGeneratedKeys((index, r, ctx) -> {
                                    Long id = r.getLong(1);
                                    city.setId(id);
                                    return id;
                                }).first()
        );
    }

    public Integer update(City city) {
        return database.getConnection().withHandle(
                handle -> handle.createStatement("update city set name = :name where id = :id")
                                .bind("id", city.getId())
                                .bind("name", city.getName())
                                .execute()
        );
    }

    public void delete(Long id) {
        database.getConnection().withHandle(
                handle -> handle.createStatement("delete from city where id = :id")
                                .bind("id", id)
                                .execute()
        );
    }

    public City findById(Long id) {
        return database.getConnection().withHandle(
                handle -> handle.createQuery("select * from city where id = :id")
                                .bind("id", id)
                                .map((index, r, ctx) -> new City(r.getLong("id"), r.getString("name")))
                                .first()
        );
    }

    public void createTableIfNotExists() {
        database.getConnection().withHandle(
                handle -> handle.createStatement(
                        "create table if not exists city (" +
                                "id bigint auto_increment primary key, " +
                                "name varchar(100))")
                                .execute()
        );
    }
}
