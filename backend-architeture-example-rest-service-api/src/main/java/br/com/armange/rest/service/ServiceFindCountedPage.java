package br.com.armange.rest.service;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import br.com.armange.dao.Dao;
import br.com.armange.entity.Identifiable;

public interface ServiceFindCountedPage<T extends Serializable, I extends Identifiable<T>> {

    Dao<T, I> getDao();
    
    default Response findCountedPage() {
        return Response.ok(getDao().findCountedPage()).build();
    }
}
