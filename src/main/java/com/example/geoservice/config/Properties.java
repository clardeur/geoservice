package com.example.geoservice.config;

import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.Resources;

public class Properties extends java.util.Properties {

    public Properties() throws IOException {
        super(new java.util.Properties());
        load("geoservice.properties");
    }

    private void load(String fileName) throws IOException {
        try (InputStream inputStream = Resources.getResource(fileName).openStream()) {
            load(inputStream);
        }
    }
}
