package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindAll;

public interface ResourceFindAll<T, I extends Identifiable<T>> {

    ServiceFindAll<T, I> getServiceFindAll();
    
    default Response findAll() {
        return getServiceFindAll().findAll();
    }
}
