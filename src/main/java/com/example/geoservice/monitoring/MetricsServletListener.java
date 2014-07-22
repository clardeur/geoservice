package com.example.geoservice.monitoring;

import javax.servlet.annotation.WebListener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

@WebListener
public class MetricsServletListener extends MetricsServlet.ContextListener {

    public static final MetricRegistry metrics = new MetricRegistry();

    @Override
    protected MetricRegistry getMetricRegistry() {
        return metrics;
    }

}
