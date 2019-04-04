package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceInsert;

public interface ResourceInsert<
        T, 
        I extends Identifiable<T>, 
        S extends ServiceInsert<T, I>> {
        
    S getService();
    
    default Response insert(final I identifiable) {
        return getService().insert(identifiable);
    }
}
