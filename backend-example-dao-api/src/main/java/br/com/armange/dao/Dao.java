package br.com.armange.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import br.com.armange.entity.Identifiable;

public interface Dao<T, I extends Identifiable<T>> {

    Class<I> getEntityClass();
    
    I save(I identifiable);
    
    void update(I identifiable);
    
    //Postponed.
    //void saveOrUpdate(I identifiable);
    
    void delete(I identifiable);
    
    //Postponed.
    //Transaction beginTransaction();
    
    I findOne(T identity);
    
    //Postponed.
    //I findOneByNative(String query, Map<String, Object> parameters);
    
    I findOneByJPQL(String query, Map<String, Object> parameters);
    
    //Postponed.
    //List<I> findManyByNative(String query, Map<String, Object> parameters);
    
    List<I> findAll();
    
<<<<<<< HEAD
    Page<I> findPage();
    
    Page<I> findCountedPage();
    
=======
>>>>>>> e2edc64... FindAll Dao method.
    List<I> findManyByJPQL(String query, Map<String, Object> parameters);
    
    Page<I> findPageByJPQL(String query, Map<String, Object> parameters, int page, int pageSize);
    
    Page<I> findCountedPageByJPQL(String query, Map<String, Object> parameters, int page, int pageSize);
    
    CriteriaBuilder getCriteriaBuilder();
}