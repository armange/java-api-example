package br.com.armange.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import br.com.armange.entity.Identifiable;

public interface Dao<T, I extends Identifiable<T>> {

    void save(I identifiable);
    
    void update(I identifiable);
    
    void saveOrUpdate(I identifiable);
    
    void delete(T identity);
    
    Transaction beginTransaction();
    
    I findOne(T identity);
    
    I findOneByNative(String query, Map<String, Object> parameters);
    
    I findOneByJPQL(String query, Map<String, Object> parameters);
    
    List<I> findManyByNative(String query, Map<String, Object> parameters);
    
    List<I> findManyByJPQL(String query, Map<String, Object> parameters);
    
    CriteriaBuilder getCriteriaBuilder();
}