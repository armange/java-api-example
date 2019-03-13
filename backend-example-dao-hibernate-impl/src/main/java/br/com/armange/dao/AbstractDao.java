package br.com.armange.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import br.com.armange.entity.Identifiable;

/*
 * The anti-pattern that is represented by opening and closing the 
 * session at each new iteration with the database will be resolved in the next changes.
 * */
public abstract class AbstractDao<T, I extends Identifiable<T>> 
        implements Dao<T, I>{

    private final SessionFactory sessionFactory;
    
    public AbstractDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public I save(final I identifiable) {
        doThroughTransacion(s -> s.save(identifiable));
        
        return identifiable;
    }

    @Override
    public void update(final I identifiable) {
        doThroughTransacion(s -> s.update(identifiable));
    }

    @Override
    public void delete(final I identifiable) {
        doThroughTransacion(s -> s.delete(identifiable));
    }

    @Override
    public I findOne(final T identity) {
        return recoverThroughTransaction(s -> s.find(getEntityClass(), identity));
    }

    @Override
    public I findOneByJPQL(final String query, final Map<String, Object> parameters) {
        return recoverThroughTransaction(s -> {
            final Query<I> createdQuery = s.createQuery(query, getEntityClass());
            
            applyQueryParameters(parameters, createdQuery);
            
            return createdQuery.getSingleResult();
        });
    }

    @Override
    public List<I> findManyByJPQL(final String query, final Map<String, Object> parameters) {
        return recoverThroughTransaction(
                createParameterizedQuery(
                        query, parameters, q -> q.getResultList()));
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return sessionFactory.getCriteriaBuilder();
    }
    
    private void doThroughTransacion(final Consumer<Session> sessionConsumer) {
        final Session session = sessionFactory.openSession();
        final org.hibernate.Transaction transaction = session.beginTransaction();
        
        try {
            sessionConsumer.accept(session);

            transaction.commit();
        } catch(final Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            
            throw e;
        } finally {
            session.close();
        }
    }
    
    private <R> R recoverThroughTransaction(final Function<Session, R> sessionFunction) {
        final Session session = sessionFactory.openSession();
        final org.hibernate.Transaction transaction = session.beginTransaction();
        
        try {
            final R result = sessionFunction.apply(session);

            transaction.commit();
            
            return result;
        } catch(final Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            
            throw e;
        } finally {
            session.close();
        }
    }
    
    private Function<Session, List<I>> createParameterizedQuery(
            final String query, 
            final Map<String, Object> parameters,
            final Function<Query<I>, List<I>> queryConsumer) {
        return s -> {
            final Query<I> createdQuery = s.createQuery(query, getEntityClass());
            
            applyQueryParameters(parameters, createdQuery);
            
            return queryConsumer.apply(createdQuery);
        };
    }
    
    private void applyQueryParameters(final Map<String, Object> parameters, final Query<I> createdQuery) {
        Optional
            .ofNullable(parameters)
            .ifPresent(
                    m -> m.entrySet().forEach(
                            e -> createdQuery.setParameter(e.getKey(), e.getValue())
                    )
            );
    }
}
