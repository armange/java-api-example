package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceInsert;

public interface ResourceInsert<T, I extends Identifiable<T>> {

    ServiceInsert<T, I> getServiceInsert();
    
    default Response insert(final I identifiable) {
        return getServiceInsert().insert(identifiable);
    }
}
