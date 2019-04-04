package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindCountedPage;

public interface ResourceFindCountedPage<
        T, 
        I extends Identifiable<T>, 
        S extends ServiceFindCountedPage<T, I>> {
        
    S getService();
    
    default Response findFindCountedPage(final T identity) {
        return getService().findCountedPage();
    }
}
