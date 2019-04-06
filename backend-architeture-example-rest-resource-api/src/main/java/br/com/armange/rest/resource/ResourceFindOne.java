package br.com.armange.rest.resource;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindOne;

public interface ResourceFindOne<
        T extends Serializable, 
        I extends Identifiable<T>, 
        S extends ServiceFindOne<T, I>> {
        
    S getService();
    
    @GET
    @Path("/{id}")
    default Response findOne(@PathParam("id") final T identity) {
        return getService().findOne(identity);
    }
}
