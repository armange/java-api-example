package br.com.armange.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;

import br.com.armange.codeless.core.StringUtil;
import br.com.armange.entity.Identifiable;
import br.com.armange.entity.Versionable;

public class DaoImpl<I extends Serializable, E extends Identifiable<I>> 
        implements Dao<I, E>{

    private static final String ENTITY_NOT_FOUND_WITH_ID = "Entity not found with ID:";
    private static final Logger LOGGER = LogManager.getLogger(DaoImpl.class);
    private final SessionFactory sessionFactory;
    private final Class<E> entityClass;
    
    public DaoImpl(final OrmServer ormServer, final Class<E> entityClass) {
        this.sessionFactory = (SessionFactory) ormServer.getOrmImpl();
        this.entityClass = entityClass;
    }
    
    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }
    
    @Override
    public E save(final E identifiable) {
        doThroughTransacion(s -> s.insert(identifiable));
        
        return identifiable;
    }

    @Override
    public void update(final E identifiable) {
        doThroughTransacion(s -> applyVersionAndUpdate(identifiable, s));
    }

    @SuppressWarnings("unchecked")
    private void applyVersionAndUpdate(final E identifiable, final StatelessSession s) {
        try {
            final E storedData = (E) s.get(entityClass, identifiable.getId());
            
            if (storedData != null) {
                applyVersion(identifiable, storedData);
                
                s.update(identifiable);
            } else {
                final IllegalArgumentException iae = new IllegalArgumentException(
                        String.join(
                                StringUtil.ONE_SPACE, ENTITY_NOT_FOUND_WITH_ID, 
                                String.valueOf(identifiable.getId())));
                
                LOGGER.error(iae.getMessage(), iae);
                
                throw iae;
            }
        } catch (final NonUniqueResultException | NullPointerException e) {
            final IllegalArgumentException iae = new IllegalArgumentException(e);
            
            LOGGER.error(iae.getMessage(), iae);
            
            throw iae;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void applyVersion(final E newEntity, final E oldEntity) {
        if (newEntity instanceof Versionable) {
            ((Versionable)newEntity).setVersion(((Versionable)oldEntity).getVersion());
        }
    }

    @Override
    public void delete(final I identity) {
        doThroughTransacion(s -> findAndDelete(identity, s));
    }
    
    @SuppressWarnings("unchecked")
    private void findAndDelete(final I identity, final StatelessSession s) {
        try {
            final E storedData = (E) s.get(entityClass, identity);
            
            if (storedData != null) {
                s.delete(storedData);
            } else {
                final IllegalArgumentException iae = new IllegalArgumentException(
                        String.join(
                                StringUtil.ONE_SPACE, ENTITY_NOT_FOUND_WITH_ID, 
                                String.valueOf(identity)));
                
                LOGGER.error(iae.getMessage(), iae);
                
                throw iae;
            }
        } catch (final NonUniqueResultException | NullPointerException e) {
            final IllegalArgumentException iae = new IllegalArgumentException(e);
            
            LOGGER.error(iae.getMessage(), iae);
            
            throw iae;
        }
    }

    @Override
    public E findOne(final I identity) {
        return recoverThroughTransaction(s -> s.find(getEntityClass(), identity));
    }

    @Override
    public E findOneByJPQL(final String query, final Map<String, Object> parameters) {
        return recoverThroughTransaction(s -> {
            final Query<E> createdQuery = s.createQuery(query, getEntityClass());
            
            applyQueryParameters(parameters, createdQuery);
            
            return createdQuery.getSingleResult();
        });
    }
    
    @Override
    public List<E> findAll() {
        return recoverThroughTransaction(s -> s.createQuery(createFindAllQuery()).getResultList());
    }
    
    @Override
    public Page<E> findPage() {
        return recoverThroughTransaction(
                createPage(createFindAllQuery(), 0, 10));
    }

    @Override
    public CountedPage<E> findCountedPage() {
        return (CountedPage<E>) recoverThroughTransaction(
                createCountedPage(createFindAllQuery(), 0, 10));
    }
    
    private CriteriaQuery<E> createFindAllQuery() {
        final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        final CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        final Root<E> rootEntry = criteriaQuery.from(getEntityClass());
        final CriteriaQuery<E> allQuery = criteriaQuery.select(rootEntry);
        
        return allQuery;
    }

    @Override
    public List<E> findManyByJPQL(final String query, final Map<String, Object> parameters) {
        return recoverThroughTransaction(
                createParameterizedQuery(
                        query, parameters, q -> q.getResultList()));
    }
    
    @Override
    public Page<E> findPageByJPQL(
            final String query, 
            final Map<String, Object> parameters,
            final int page,
            final int pageSize) {
        return recoverThroughTransaction(
                createParameterizedPage(
                        query, parameters, getPageResult(page, pageSize, null)));
    }
    
    @Override
    public CountedPage<E> findCountedPageByJPQL(
            final String query, 
            final Map<String, Object> parameters,
            final int page,
            final int pageSize) {
        return recoverThroughTransaction(
                createParameterizedCountedPage(query, parameters, page, pageSize));
    }
    
    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return sessionFactory.getCriteriaBuilder();
    }
    
    private void doThroughTransacion(final Consumer<StatelessSession> sessionConsumer) {
        final StatelessSession session = sessionFactory.openStatelessSession();
        final org.hibernate.Transaction transaction = session.beginTransaction();
        
        try {
            sessionConsumer.accept(session);

            transaction.commit();
        } catch(final Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            
            LOGGER.error(e.getMessage(), e);
            
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
            
            LOGGER.error(e.getMessage(), e);
            
            throw e;
        } finally {
            session.close();
        }
    }
    
    private Function<Session, List<E>> createParameterizedQuery(
            final String query, 
            final Map<String, Object> parameters,
            final Function<Query<E>, List<E>> queryConsumer) {
        return s -> {
            final Query<E> createdQuery = s.createQuery(query, getEntityClass());
            
            applyQueryParameters(parameters, createdQuery);
            
            return queryConsumer.apply(createdQuery);
        };
    }
    
    private Function<Session, Page<E>> createParameterizedPage(
            final String query, 
            final Map<String, Object> parameters,
            final Function<Query<E>, Page<E>> queryConsumer) {
        return s -> {
            final Query<E> createdQuery = s.createQuery(query, getEntityClass());
            
            applyQueryParameters(parameters, createdQuery);
            
            return queryConsumer.apply(createdQuery);
        };
    }
    
    private Function<Session, Page<E>> createPage(
            final CriteriaQuery<E> query, 
            final int page, 
            final int pageSize) {
        return s -> {
            final Query<E> createdQuery = s.createQuery(query);
            
            return getPageResult(page, pageSize, null).apply(createdQuery);
        };
    }
    
    private Function<Session, Page<E>> createCountedPage(
            final CriteriaQuery<E> query, 
            final int page, 
            final int pageSize) {
        return session -> {
            final Long pageCount = pageCount(session, pageSize);
            
            final Query<E> createdQuery = session.createQuery(query);
            
            return getPageResult(page, pageSize, pageCount).apply(createdQuery);
        };
    }
    
    private Function<Session, CountedPage<E>> createParameterizedCountedPage(
            final String query, 
            final Map<String, Object> parameters,
            final int page, 
            final int pageSize) {
        return session -> {
            final Long pageCount = pageCount(session, pageSize);
            
            final Query<E> createdQuery = session.createQuery(query, getEntityClass());
            
            applyQueryParameters(parameters, createdQuery);
            
            return (CountedPage<E>) getPageResult(page, pageSize, pageCount).apply(createdQuery);
        };
    }

    private Long pageCount(final Session session, final int pageSize) {
        final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getEntityClass())));
        
        final Long rowsCount = session.createQuery(criteriaQuery).getSingleResult();
        
        if (rowsCount > 0) {
            return rowsCount >= pageSize ? rowsCount / pageSize : 1;
        } else {
            return 0L;
        }
    }
    
    private Function<Query<E>, Page<E>> getPageResult(final int page, final int pageSize, final Long pageCount) {
        final int pageStart = page * pageSize - page;
        
        if (pageCount == null) {
            return q -> new PageImpl<>(q.getResultList(), page, pageStart, pageSize);
        } else {
            return q -> new CountedPageImpl<>(q.getResultList(), page, pageStart, pageSize, pageCount);
        }
    }
    
    private void applyQueryParameters(final Map<String, Object> parameters, final Query<E> createdQuery) {
        Optional
            .ofNullable(parameters)
            .ifPresent(
                    m -> m.entrySet().forEach(
                            e -> createdQuery.setParameter(e.getKey(), e.getValue())
                    )
            );
    }
}
