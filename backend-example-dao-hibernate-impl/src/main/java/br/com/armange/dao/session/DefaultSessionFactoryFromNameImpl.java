package br.com.armange.dao.session;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.cfg.Environment;

import br.com.armange.dao.configuration.DaoProperties;

@ApplicationScoped
public class DefaultSessionFactoryFromNameImpl 
        extends AbstractSessionFactoryFromName 
        implements SessionFactoryFromName {
    
    private static final DefaultSessionFactoryFromNameImpl INSTANCE = 
            new DefaultSessionFactoryFromNameImpl();
    
    private DefaultSessionFactoryFromNameImpl() {}

    public org.hibernate.SessionFactory fromName(final String name) {
        final String propetiesPrefix = String.join(".", DaoProperties.PREFIX, name);
        
        Environment.BYTECODE_PROVIDER_NAME_NONE
        
        return null;
    }
    
    public static DefaultSessionFactoryFromNameImpl getInstance() {
        return INSTANCE;
    }
}
