package br.com.armange.dao.configuration;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.core.api.config.Configuration;

@Configuration(prefix=DaoProperties.PREFIX)
public interface DaoProperties {
    public static final String PREFIX = "br.com.armange.dao.hibernate";
    public static final String DEFAULT_SESSION_FACTORY_KEY = "default.session.factory";
    public static final String ENTITY_CLASS_LIST_KEY = "entity-class-list";
    public static final String ENTITY_PACKAGE_LIST_KEY = "entity-package-list";
    
    @ConfigProperty(name = DEFAULT_SESSION_FACTORY_KEY)
    String defaultSessionFactory();
}
