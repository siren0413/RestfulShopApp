package com.restful.shop.services;

import com.restful.shop.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yijunmao on 11/4/14.
 */

@Path("customers")
public interface CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomerByXML(Customer customer);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomerByJSON(InputStream is);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Customer getCustomer(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateCustomer(@PathParam("id") int id, Customer customer);

    @GET
    @Produces({"application/xml","application/json"})
    public List<Customer> getCustomers();

    @GET
    @Path("all")
    @Produces(MediaType.TEXT_PLAIN)
    public String greet();
}
