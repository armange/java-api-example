package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindPage;

public interface ResourceFindPage<T, I extends Identifiable<T>> {

    ServiceFindPage<T, I> getServiceFindPage();
    
    default Response findOne(final T identity) {
        return getServiceFindPage().findPage();
    }
}
