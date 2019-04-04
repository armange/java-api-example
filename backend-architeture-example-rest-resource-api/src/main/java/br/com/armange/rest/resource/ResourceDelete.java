package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceDelete;

public interface ResourceDelete<
        T, 
        I extends Identifiable<T>, 
        S extends ServiceDelete<T, I>> {

    S getService();
    
    default Response delete(final I identifiable) {
        return getService().delete(identifiable);
    }
}
