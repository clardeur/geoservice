package com.example.geoservice.monitoring;

import javax.servlet.annotation.WebListener;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

@WebListener
public class HealthCheckServletListener extends HealthCheckServlet.ContextListener {

    public static final HealthCheckRegistry healthCheck = new HealthCheckRegistry();

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheck;
    }

}
