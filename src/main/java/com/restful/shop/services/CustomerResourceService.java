package com.restful.shop.services;

import com.restful.shop.model.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
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
    public Response createCustomer(InputStream is) {
        Customer customer = readCustomer(is);
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(), customer);
        System.out.println("Created customer " + customer.getId());
        return Response.created(URI.create("/customers/" + customer.getId())).build();
    }


    @Override
    public StreamingOutput getCustomer(int id) {
        final Customer customer = customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
            outputCustomer(outputStream, customer);
        }
    };
}


    @Override
    public void updateCustomer(int id, InputStream is) {
        Customer update = readCustomer(is);
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
    public StreamingOutput getCustomers() {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                for(Customer customer: customerDB.values()){
                    outputCustomer(outputStream, customer);
                }
            }
        };
    }

    @Override
    public String greet() {
        return "hello";
    }

    private Customer readCustomer(InputStream is) {
        try {
            DocumentBuilder builder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();

            Customer cust = new Customer();
            if (root.getAttribute("id") != null
                    && !root.getAttribute("id").trim().equals("")) {
                cust.setId(Integer.valueOf(root.getAttribute("id")));
            }
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (element.getTagName().equals("first-name")) {
                    cust.setFirstName(element.getTextContent());
                } else if (element.getTagName().equals("last-name")) {
                    cust.setLastName(element.getTextContent());
                } else if (element.getTagName().equals("street")) {
                    cust.setStreet(element.getTextContent());
                } else if (element.getTagName().equals("city")) {
                    cust.setCity(element.getTextContent());
                } else if (element.getTagName().equals("state")) {
                    cust.setState(element.getTextContent());
                } else if (element.getTagName().equals("zip")) {
                    cust.setZip(element.getTextContent());
                } else if (element.getTagName().equals("country")) {
                    cust.setCountry(element.getTextContent());
                }
            }
            return cust;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
        }
    }

    private void outputCustomer(OutputStream os, Customer cust) {
        PrintStream writer = new PrintStream(os);
        writer.println("<customer id=\"" + cust.getId() + "\">");
        writer.println(" <first-name>" + cust.getFirstName()
                + "</first-name>");
        writer.println(" <last-name>" + cust.getLastName()
                + "</last-name>");
        writer.println(" <street>" + cust.getStreet() + "</street>");
        writer.println(" <city>" + cust.getCity() + "</city>");
        writer.println(" <state>" + cust.getState() + "</state>");
        writer.println(" <zip>" + cust.getZip() + "</zip>");
        writer.println(" <country>" + cust.getCountry() + "</country>");
        writer.println("</customer>");
    }

}
