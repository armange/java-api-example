package br.com.armange.dao.session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.armange.codeless.core.StringUtil;
import br.com.armange.codeless.reflection.clazz.ClassReflectionClosures;
import br.com.armange.dao.configuration.DaoProperties;

public class NamedSessionFactory {
    private static final Map<String, org.hibernate.SessionFactory> sessionFactotyMap = new HashMap<>();
    
    private NamedSessionFactory() {}
    
    public static SessionFactory fromName(final String name) {
        SessionFactory sessionFactory = sessionFactotyMap.get(name);
        
        if (sessionFactory == null) {
            sessionFactory = generateSessionFactoryFromName(name);
            sessionFactotyMap.put(name, sessionFactory);
        }
        
        return sessionFactory;
    }

    private static SessionFactory generateSessionFactoryFromName(final String name) {
        final String propetiesPrefix = buildPropertiesPrefix(name);
        final Map<String, String> settings = findHibernateProperties(propetiesPrefix);
        final List<String> entityClassList = findEntityClassList(settings);
        final List<String> entityPackageList = findEntityPackageList(settings);
        final Configuration configuration = new Configuration();
        final Properties properties = new Properties();
        
        properties.putAll(settings);
        configuration.setProperties(properties);
        
        addAnnotatedClass(entityClassList, configuration);
        addPackage(entityPackageList, configuration);
        
        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static String buildPropertiesPrefix(final String name) {
        return String.join(
                StringUtil.EMPTY, DaoProperties.PREFIX, StringUtil.DOT, name, StringUtil.DOT);
    }

    private static Map<String, String> findHibernateProperties(final String propetiesPrefix) {
        return findDaoProperties().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().replaceFirst(propetiesPrefix, StringUtil.EMPTY), 
                        Entry::getValue));
    }
    
    private static Map<String, String> findDaoProperties() {
        return Optional.ofNullable(ConfigResolver.getAllProperties())
                .orElse(new HashMap<>())
                .entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith(DaoProperties.PREFIX))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
    
    private static List<String> findEntityClassList(final Map<String, String> settings) {
        return Arrays.asList(
                Optional
                    .ofNullable(settings.get(DaoProperties.ENTITY_CLASS_LIST_KEY))
                    .orElse(StringUtil.EMPTY).split(StringUtil.COMMA));
    }
    
    private static List<String> findEntityPackageList(final Map<String, String> settings) {
        return Arrays.asList(
                Optional
                .ofNullable(settings.get(DaoProperties.ENTITY_PACKAGE_LIST_KEY))
                .orElse(StringUtil.EMPTY).split(StringUtil.COMMA));
    }
    
    private static void addAnnotatedClass(final List<String> entityClassList, final Configuration configuration) {
        entityClassList
            .stream()
            .map(ClassReflectionClosures.mapToClass())
            .forEach(configuration::addAnnotatedClass);
    }
    
    private static void addPackage(final List<String> entityPackageList, final Configuration configuration) {
        entityPackageList.forEach(configuration::addPackage);
    }
}
