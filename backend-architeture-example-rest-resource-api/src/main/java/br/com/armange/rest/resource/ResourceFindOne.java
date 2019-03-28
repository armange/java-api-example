package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindOne;

public interface ResourceFindOne<T, I extends Identifiable<T>> {

    ServiceFindOne<T, I> getServiceFindOne();
    
    default Response findOne(final T identity) {
        return getServiceFindOne().findOne(identity);
    }
}
