package com.example.geoservice.monitoring;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

public class MetricsServletListener extends MetricsServlet.ContextListener {

    public static final MetricRegistry registry = new MetricRegistry();

    @Override
    protected MetricRegistry getMetricRegistry() {
        return registry;
    }

}
