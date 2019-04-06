package br.com.armange.rest.service;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import br.com.armange.dao.Dao;
import br.com.armange.entity.Identifiable;

public interface ServiceDelete<T extends Serializable, I extends Identifiable<T>> {

    Dao<T, I> getDao();
    
    default Response delete(final T identity) {
        getDao().delete(identity);
        
        return Response.noContent().build();
    }
}
