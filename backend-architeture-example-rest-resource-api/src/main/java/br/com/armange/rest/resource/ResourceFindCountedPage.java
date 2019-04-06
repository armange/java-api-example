package br.com.armange.rest.resource;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindCountedPage;

public interface ResourceFindCountedPage<
        T extends Serializable, 
        I extends Identifiable<T>, 
        S extends ServiceFindCountedPage<T, I>> {
        
    S getService();
    
    @GET
    @Path("/counted-page")
    default Response findFindCountedPage() {
        return getService().findCountedPage();
    }
}
