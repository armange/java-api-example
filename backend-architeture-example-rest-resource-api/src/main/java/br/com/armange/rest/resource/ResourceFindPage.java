package br.com.armange.rest.resource;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceFindPage;

public interface ResourceFindPage<
        T extends Serializable, 
        I extends Identifiable<T>, 
        S extends ServiceFindPage<T, I>> {
        
    S getService();
    
    @GET
    @Path("/page")
    default Response findFindPage() {
        return getService().findPage();
    }
}
