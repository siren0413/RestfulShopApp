package com.restful.shop.app;

import com.restful.shop.services.CustomerResourceService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yijunmao on 11/4/14.
 */
@ApplicationPath("/")
public class ShoppingApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    public ShoppingApplication(){

        //singletons.add(new CustomerResourceService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        empty.add(CustomerResourceService.class);
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
