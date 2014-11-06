package com.restful.shop.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;

/**
 * Created by yijunmao on 11/4/14.
 */

@Path("customers")
public interface CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomer(InputStream is);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomer(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateCustomer(@PathParam("id") int id, InputStream is);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomers();

    @GET
    @Path("all")
    @Produces(MediaType.TEXT_PLAIN)
    public String greet();
}
