package com.example.geoservice.repository;

import static com.example.geoservice.monitoring.MetricsServletListener.metrics;

import javax.inject.Inject;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

import com.codahale.metrics.jdbi.InstrumentedTimingCollector;
import com.example.geoservice.config.Properties;

public class Database {

    private final DBI connection;

    @Inject
    public Database(Properties properties) {
        String url = properties.getProperty("database.jdbc.url", "jdbc:h2:mem:test");
        String user = properties.getProperty("database.jdbc.user", "user");
        String password = properties.getProperty("database.jdbc.password", "");
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create(url, user, password);

        Integer maxConnectionPool = Integer.valueOf(properties.getProperty("database.max.connection.pool"), 5);
        connectionPool.setMaxConnections(maxConnectionPool);

        this.connection = new DBI(connectionPool);
        this.connection.setTimingCollector(new InstrumentedTimingCollector(metrics));
    }

    public Database(DBI connection) {
        this.connection = connection;
    }

    public DBI getConnection() {
        return connection;
    }
}
