package com.example.geoservice.monitoring;

import static org.assertj.core.api.Assertions.assertThat;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.codahale.metrics.health.HealthCheck;
import com.example.geoservice.repository.Database;

public class DatabaseHealthCheckTest {

    private Database database;

    private DatabaseHealthCheck databaseHealthCheck;

    @Before
    public void setUp() throws Exception {
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test", "user", "");
        connectionPool.setMaxConnections(1);
        connectionPool.setLoginTimeout(1);
        this.database = new Database(new DBI(connectionPool));
        this.databaseHealthCheck = new DatabaseHealthCheck(database);
    }

    @Test
    public void should_return_ok() throws Exception {
        assertThat(databaseHealthCheck.check()).isEqualTo(HealthCheck.Result.healthy());
    }

    @Test
    public void should_return_failed() throws Exception {
        try (Handle handle = database.getConnection().open()) {
            assertThat(databaseHealthCheck.check()).isEqualTo(HealthCheck.Result.unhealthy("java.sql.SQLException: Login timeout"));
        }
    }
}
