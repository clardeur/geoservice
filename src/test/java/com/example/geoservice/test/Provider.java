package com.example.geoservice.test;

import static com.google.common.base.Preconditions.checkNotNull;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class Provider<T> extends AbstractBinder implements Factory<T> {

    private final T instance;

    public Provider(T instance) {
        checkNotNull(instance, "instance is required");
        this.instance = instance;
    }

    @Override
    protected void configure() {
        bindFactory(this).to(instance.getClass());
    }

    @Override
    public T provide() {
        return instance;
    }

    @Override
    public void dispose(T t) {
    }
}
