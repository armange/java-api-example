package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindPage;

public interface ResourceFindPage<
        T, 
        I extends Identifiable<T>, 
        S extends ServiceFindPage<T, I>> {
        
    S getService();
    
    default Response findFindPage(final T identity) {
        return getService().findPage();
    }
}
