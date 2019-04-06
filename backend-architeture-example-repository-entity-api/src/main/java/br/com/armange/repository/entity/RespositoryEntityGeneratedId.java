package br.com.armange.repository.entity;

import java.io.Serializable;

import br.com.armange.dao.Dao;
import br.com.armange.entity.BaseEntityGeneratedId;
import br.com.armange.entity.Identifiable;

public abstract class RespositoryEntityGeneratedId<T extends Serializable, I extends Identifiable<T>> 
        extends BaseEntityGeneratedId<T> {
    
    protected final Dao<T, I> dao;
    
    public RespositoryEntityGeneratedId() {
        this.dao = null;
    }
    
    public RespositoryEntityGeneratedId(final Dao<T, I> dao) {
        this.dao = dao;
    }
}
