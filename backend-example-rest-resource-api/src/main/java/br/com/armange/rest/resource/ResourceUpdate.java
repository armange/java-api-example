package br.com.armange.rest.resource;

import javax.ws.rs.core.Response;

import br.com.armange.entity.Identifiable;
import br.com.armange.rest.service.ServiceUpdate;

public interface ResourceUpdate<T, I extends Identifiable<T>> {

    ServiceUpdate<T, I> getServiceUpdate();
    
    default Response update(final I identifiable) {
        return getServiceUpdate().update(identifiable);
    }
}
