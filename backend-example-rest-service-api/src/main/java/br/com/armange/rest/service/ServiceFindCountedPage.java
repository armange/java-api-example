package br.com.armange.rest.service;

import javax.ws.rs.core.Response;

import br.com.armange.dao.Dao;
import br.com.armange.entity.Identifiable;

public interface ServiceFindCountedPage<T, I extends Identifiable<T>> {

    Dao<T, I> getDao();
    
    default Response findCountedPage(final I identifiable) {
        return Response.ok(getDao().findAll()).build();
    }
}
