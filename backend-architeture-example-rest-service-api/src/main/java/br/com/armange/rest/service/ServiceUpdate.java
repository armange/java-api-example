package br.com.armange.rest.service;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.armange.dao.Dao;
import br.com.armange.entity.Identifiable;

public interface ServiceUpdate<T extends Serializable, I extends Identifiable<T>> {

    Dao<T, I> getDao();
    
    default Response update(final I identifiable) {
        getDao().update(identifiable);
        
        return Response.status(Status.NO_CONTENT).build();
    }
}
