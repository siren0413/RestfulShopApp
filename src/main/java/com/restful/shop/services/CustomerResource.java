package com.restful.shop.services;

import com.restful.shop.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yijunmao on 11/4/14.
 */

@Path("customers")
public interface CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomer(Customer customer);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Customer getCustomer(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateCustomer(@PathParam("id") int id, Customer customer);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Customer> getCustomers();

    @GET
    @Path("all")
    @Produces(MediaType.TEXT_PLAIN)
    public String greet();
}
