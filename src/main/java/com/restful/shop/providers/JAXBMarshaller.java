package com.restful.shop.providers;

import com.restful.shop.annotations.Pretty;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by yijunmao on 11/6/14.
 */

@Provider
@Produces(MediaType.APPLICATION_XML)
public class JAXBMarshaller implements MessageBodyWriter {

    @Context
    protected Providers providers;

    public JAXBMarshaller(@Context Providers providers){
        this.providers = providers;
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.isAnnotationPresent(XmlRootElement.class);
    }

    @Override
    public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        try{
            JAXBContext context = null;
            ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
            if(resolver!=null){
                context = resolver.getContext(type);
            }
            if(context == null){
                context = JAXBContext.newInstance(type);
            }
            Marshaller m = context.createMarshaller();

            boolean pretty = false;
            for(Annotation ann: annotations){
                if(ann.annotationType().equals(Pretty.class)){
                    pretty = true;
                    break;
                }
            }
            if(pretty){
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            }
            m.marshal(o, entityStream);
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}
