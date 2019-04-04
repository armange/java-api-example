package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindAll;

public interface ResourceFindAll<
        T, 
        I extends Identifiable<T>, 
        S extends ServiceFindAll<T, I>> {
        
    S getService();
    
    default Response findAll() {
        return getService().findAll();
    }
}
