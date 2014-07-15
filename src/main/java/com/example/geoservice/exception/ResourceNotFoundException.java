package com.example.geoservice.exception;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ResourceNotFoundException extends WebApplicationException {

    public ResourceNotFoundException() {
        super(Response.status(NOT_FOUND)
                      .entity("resource not found")
                      .type(TEXT_PLAIN_TYPE)
                      .build());
    }

}
