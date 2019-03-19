package br.com.armange.rest.service;

import javax.ws.rs.core.Response;

import br.com.armange.dao.Dao;
import br.com.armange.entity.Identifiable;

public interface ServiceDelete<T, I extends Identifiable<T>> {

    Dao<T, I> getDao();
    
    default Response delete(final I identifiable) {
        getDao().delete(identifiable);
        
        return Response.noContent().build();
    }
}
