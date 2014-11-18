package com.restful.shop.app;

import com.restful.shop.providers.JAXBMarshaller;
import com.restful.shop.providers.JSONMarshaller;
import com.restful.shop.services.CustomerResourceService;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.spi.Registry;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yijunmao on 11/4/14.
 */
@ApplicationPath("/")
public class ShoppingApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public ShoppingApplication(@Context final Dispatcher dispatcher) {
        //classes.add(CustomerResourceService.class);
        classes.add(JAXBMarshaller.class);
        classes.add(JSONMarshaller.class);
        //singletons.add(new CustomerResourceService());
        final Registry registry = dispatcher.getRegistry();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                registry.addPerRequestResource(CustomerResourceService.class);
            }
        }).start();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
