package com.example.geoservice.repository;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

public class Database {

    private final DBI connection;

    public Database() {
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test", "user", "");
        connectionPool.setMaxConnections(5);
        this.connection = new DBI(connectionPool);
    }

    public DBI getConnection() {
        return connection;
    }
}
