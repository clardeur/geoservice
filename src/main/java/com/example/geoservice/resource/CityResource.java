package com.example.geoservice.resource;

import static java.lang.Long.parseLong;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.example.geoservice.monitoring.Timed;
import com.example.geoservice.repository.CityRepository;

@Path("/city")
@Produces(APPLICATION_JSON)
public class CityResource {

    private CityRepository repository;

    @Inject
    public CityResource(CityRepository repository) {
        this.repository = repository;
    }

    @GET
    @Timed
    @Path("/{id:[0-9]+}")
    public Response get(@PathParam("id") String id) {
        return Response.ok(repository.findById(parseLong(id))).build();
    }
}