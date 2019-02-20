package br.com.armange.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.cfg.Environment;

import br.com.armange.entity.Identifiable;

public class DaoImpl<T, I extends Identifiable<T>> 
        implements Dao<T, I>{

    @Override
    public void save(final I identifiable) {
        String s = Environment.PASS;
    }

    @Override
    public void update(final I identifiable) {
        
    }

    @Override
    public void saveOrUpdate(final I identifiable) {
        
    }

    @Override
    public void delete(final T identity) {
        
    }

    @Override
    public Transaction beginTransaction() {
        return null;
    }

    @Override
    public I findOne(final T identity) {
        return null;
    }

    @Override
    public I findOneByNative(final String query, final Map<String, Object> parameters) {
        return null;
    }

    @Override
    public I findOneByJPQL(final String query, final Map<String, Object> parameters) {
        return null;
    }

    @Override
    public List<I> findManyByNative(final String query, final Map<String, Object> parameters) {
        return null;
    }

    @Override
    public List<I> findManyByJPQL(final String query, final Map<String, Object> parameters) {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }
}
