package br.com.armange.dao.artifacts;

import org.hibernate.SessionFactory;

import br.com.armange.dao.AbstractDao;

public class TestEntityDao extends AbstractDao<Long, TestEntity> {

    public TestEntityDao(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<TestEntity> getEntityClass() {
        return TestEntity.class;
    }
}
