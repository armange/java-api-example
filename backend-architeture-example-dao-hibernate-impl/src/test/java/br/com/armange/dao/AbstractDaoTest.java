package br.com.armange.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.armange.codeless.core.StringUtil;
import br.com.armange.dao.artifacts.TestEntity;
import br.com.armange.dao.artifacts.TestEntityDao;

public class AbstractDaoTest {

    private static final String ID = "id";
    private static final String HELLO_HIBERNATE = "Hello Hibernate";
    private static final String HELLO_H2 = "Hello H2";
    private final OrmServer ormServer = new OrmServerImpl("h2");
    
    @Test
    public void daoMustSaveEntity() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        entity.setDescription(HELLO_HIBERNATE);
        dao.save(entity);
        
        final TestEntity entityFound = dao.findOne(entity.getId());
        final SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThat(entityFound).isNotNull();
        softAssertions.assertThat(entityFound.getId()).isNotNull();
        softAssertions.assertThat(entityFound.getDescription()).isEqualTo(HELLO_HIBERNATE);
        softAssertions.assertAll();
    }
    
    @Test
    public void daoMustUpdateEntity() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        entity.setDescription(HELLO_HIBERNATE);
        dao.save(entity);
        
        entity.setDescription(HELLO_H2);
        dao.update(entity);
        
        final TestEntity entityFound = dao.findOne(entity.getId());
        final SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThat(entityFound).isNotNull();
        softAssertions.assertThat(entityFound.getId()).isNotNull();
        softAssertions.assertThat(entityFound.getDescription()).isEqualTo(HELLO_H2);
        softAssertions.assertAll();
    }
    
    @Test
    public void daoMustDeleteEntity() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        entity.setDescription(HELLO_HIBERNATE);
        dao.save(entity);
        dao.delete(entity);
        
        final TestEntity entityFound = dao.findOne(entity.getId());
        
        Assertions.assertThat(entityFound).isNull();
    }
    
    @Test
    public void daoMustFindOne() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        dao.save(entity);
        
        final TestEntity entityFound = dao.findOne(entity.getId());
        
        Assertions.assertThat(entityFound).isNotNull();
    }
    
    @Test
    public void daoMustFindOneByJPQL() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        entity.setDescription(HELLO_HIBERNATE);
        dao.save(entity);
        
        @SuppressWarnings("serial")
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put(ID,entity.getId());
        }};
        
        final TestEntity entityFound = dao.findOneByJPQL("from TestEntity where id = :id", parameters);
        final SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThat(entityFound).isNotNull();
        softAssertions.assertThat(entityFound.getId()).isEqualTo(entity.getId());
        softAssertions.assertAll();
    }
    
    @Test
    public void daoMustFindManyByJPQL() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        entity.setDescription(HELLO_HIBERNATE);
        dao.save(entity);
        
        @SuppressWarnings("serial")
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put(ID,entity.getId());
        }};
        
        final List<TestEntity> entityFound = dao.findManyByJPQL("from TestEntity where id = :id", parameters);
        final SoftAssertions softAssertions = new SoftAssertions();
                
        softAssertions.assertThat(entityFound).isNotNull();
        softAssertions.assertThat(entityFound).isNotEmpty();
        softAssertions.assertAll();
        
        Assert.assertThat(entityFound, 
                Matchers.hasItem(
                        Matchers.<TestEntity>hasProperty(ID, 
                                Matchers.equalTo(entity.getId()))));
    }
    
    @Test
    public void daoMustProvideCriteriaBuilder() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final Object criteriaBuilder = dao.getCriteriaBuilder();
        final SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThat(criteriaBuilder).isNotNull();
        softAssertions.assertThat(criteriaBuilder).isInstanceOf(CriteriaBuilder.class);
        softAssertions.assertAll();
    }
    
    @Test(expected=org.hibernate.TransientObjectException.class)
    public void daoMustThrowExceptionWhenFailOnDoAnything() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        final TestEntity entity = new TestEntity();
        
        dao.update(entity);
    }
    
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void daoMustThrowExceptionWhenFailOnRetrieveAnything() {
        final TestEntityDao dao = new TestEntityDao(ormServer, TestEntity.class);
        
        dao.findManyByJPQL(StringUtil.EMPTY, null);
    }
}
