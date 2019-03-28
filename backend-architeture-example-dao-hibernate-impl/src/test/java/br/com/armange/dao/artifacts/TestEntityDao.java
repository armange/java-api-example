package br.com.armange.dao.artifacts;

import br.com.armange.dao.DaoImpl;
import br.com.armange.dao.OrmServer;

public class TestEntityDao extends DaoImpl<Long, TestEntity> {

    public TestEntityDao(final OrmServer ormServer, final Class<TestEntity> entityClass) {
        super(ormServer, entityClass);
    }

    @Override
    public Class<TestEntity> getEntityClass() {
        return TestEntity.class;
    }
}
