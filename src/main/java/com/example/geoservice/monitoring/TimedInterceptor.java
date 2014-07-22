package com.example.geoservice.monitoring;

import static com.example.geoservice.monitoring.MetricsServletListener.metrics;
import static com.google.common.base.Strings.emptyToNull;

import java.lang.reflect.Method;
import java.util.Optional;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class TimedInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        Timer timer = metrics.timer(getMetricName(method.getAnnotation(Timed.class), method));

        final Timer.Context ctx = timer.time();
        try {
            return invocation.proceed();
        }
        finally {
            ctx.stop();
        }
    }

    String getMetricName(Timed annotation, Method method) {
        String name = Optional.ofNullable(emptyToNull(annotation.name()))
                              .orElse(method.getName());

        return MetricRegistry.name(method.getDeclaringClass().getName(), name);
    }
}
