package com.restful.shop.services;

import com.restful.shop.model.Customer;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yijunmao on 11/4/14.
 */
public class CustomerResourceService implements CustomerResource {

    private static Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    @Override
    public Response createCustomer(Customer customer) {
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(), customer);
        System.out.println("Created customer " + customer.getId());
        return Response.created(URI.create("/customers/" + customer.getId())).build();
    }

    @Override
    public Customer getCustomer(int id) {
        final Customer customer = customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return customer;
    }

    @Override
    public void updateCustomer(int id, Customer update) {
        Customer current = customerDB.get(id);
        if (current == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);

        current.setFirstName(update.getFirstName());
        current.setLastName(update.getLastName());
        current.setCity(update.getCity());
        current.setCountry(update.getCountry());
        current.setState(update.getState());
        current.setStreet(update.getStreet());
        current.setZip(update.getZip());
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> list = new LinkedList<>();
        for(Customer customer: customerDB.values()){
            list.add(customer);
        }
        return list;
    }

    @Override
    public String greet() {
        return "hello";
    }


}
