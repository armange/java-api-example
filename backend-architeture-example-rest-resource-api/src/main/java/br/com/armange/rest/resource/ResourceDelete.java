package br.com.armange.rest.resource;

import java.io.Serializable;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceDelete;

public interface ResourceDelete<
        T extends Serializable, 
        I extends Identifiable<T>, 
        S extends ServiceDelete<T, I>> {

    S getService();
    
    @DELETE
    @Path("/{id}")
    default Response delete(@PathParam("id") final T identity) {
        return getService().delete(identity);
    }
}
