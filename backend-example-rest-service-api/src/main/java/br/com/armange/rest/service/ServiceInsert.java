package br.com.armange.rest.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.armange.dao.Dao;
import br.com.armange.entity.Identifiable;

public interface ServiceInsert<T, I extends Identifiable<T>> {

    Dao<T, I> getDao();
    
    default Response insert(final I identifiable) {
        return Response
                .status(Status.CREATED)
                .entity(getDao().save(identifiable))
                .build();
    }
}
