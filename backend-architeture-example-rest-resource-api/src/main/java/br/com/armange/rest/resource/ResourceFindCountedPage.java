package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindCountedPage;

public interface ResourceFindCountedPage<T, I extends Identifiable<T>> {

    ServiceFindCountedPage<T, I> getServiceFindCountedPage();
    
    default Response findOne(final T identity) {
        return getServiceFindCountedPage().findCountedPage();
    }
}
