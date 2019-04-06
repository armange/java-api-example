package br.com.armange.rest.resource;

import java.io.Serializable;

import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceUpdate;

public interface ResourceUpdate<
        T extends Serializable, 
        I extends Identifiable<T>, 
        S extends ServiceUpdate<T, I>> {
        
    S getService();
    
    @PUT
    default Response update(final I identifiable) {
        return getService().update(identifiable);
    }
}
