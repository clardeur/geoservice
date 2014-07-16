package com.example.geoservice.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.BuilderHelper;

import com.example.geoservice.monitoring.Timed;
import com.example.geoservice.monitoring.TimedInterceptor;
import com.google.common.collect.Lists;

public class GeoserviceInterception implements InterceptionService {

    @Override
    public Filter getDescriptorFilter() {
        return BuilderHelper.allFilter();
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        if (method.getAnnotation(Timed.class) != null) {
            return Lists.<MethodInterceptor>newArrayList(new TimedInterceptor());
        }

        return Collections.emptyList();
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
        return Collections.emptyList();
    }
}
