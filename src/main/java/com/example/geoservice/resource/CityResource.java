package com.example.geoservice.resource;

import static java.lang.Long.parseLong;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.geoservice.domain.City;
import com.example.geoservice.exception.ResourceNotFoundException;
import com.example.geoservice.monitoring.Timed;
import com.example.geoservice.repository.CityRepository;

@Path("/city")
@Produces(APPLICATION_JSON)
public class CityResource {

    @Context
    UriInfo uriInfo;

    private CityRepository repository;

    @Inject
    public CityResource(CityRepository repository) {
        this.repository = repository;
    }

    @GET
    @Timed
    @Path("/{id:[0-9]+}")
    @RolesAllowed("USER")
    public Response get(@PathParam("id") String id) {
        City city = repository.findById(parseLong(id));
        if (city == null) {
            throw new ResourceNotFoundException();
        }
        return Response.ok(city).build();
    }

    @POST
    @Timed
    public Response create(City city) {
        repository.insert(city);

        return Response.created(uriInfo.getRequestUriBuilder().path(city.getId().toString()).build())
                       .entity(city)
                       .build();
    }
}