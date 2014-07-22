package com.example.geoservice.monitoring;

import static com.example.geoservice.monitoring.HealthCheckServletListener.healthCheck;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.codahale.metrics.health.HealthCheck;
import com.example.geoservice.repository.Database;

public class DatabaseHealthCheck extends HealthCheck {

    private final Database database;

    @Inject
    public DatabaseHealthCheck(Database database) {
        checkNotNull(database);
        this.database = database;
        healthCheck.register("database", this);
    }

    @Override
    protected Result check() throws Exception {
        try {
            database.getConnection().withHandle(handle -> handle.createQuery("select 1"));
        }
        catch (Exception e) {
            return Result.unhealthy(e.getMessage());
        }

        return Result.healthy();
    }
}
