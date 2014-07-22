package com.example.geoservice.repository;

import static com.example.geoservice.monitoring.MetricsServletListener.registry;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

import com.codahale.metrics.jdbi.InstrumentedTimingCollector;

public class Database {

    private final DBI connection;

    public Database() {
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test", "user", "");
        connectionPool.setMaxConnections(5);
        this.connection = new DBI(connectionPool);
        this.connection.setTimingCollector(new InstrumentedTimingCollector(registry));
    }

    public DBI getConnection() {
        return connection;
    }
}
