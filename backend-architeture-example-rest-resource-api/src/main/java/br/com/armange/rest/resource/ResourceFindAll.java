package br.com.armange.rest.resource;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindAll;

public interface ResourceFindAll<
        T extends Serializable, 
        I extends Identifiable<T>, 
        S extends ServiceFindAll<T, I>> {
        
    S getService();
    
    @GET
    default Response findAll() {
        return getService().findAll();
    }
}
