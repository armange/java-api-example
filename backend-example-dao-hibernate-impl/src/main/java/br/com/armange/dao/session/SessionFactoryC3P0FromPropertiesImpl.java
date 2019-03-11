package br.com.armange.dao.session;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionFactoryC3P0FromPropertiesImpl 
    extends AbstractSessionFactoryFromName 
    implements SessionFactoryFromName {
    
    private SessionFactoryC3P0FromPropertiesImpl() {}

    public org.hibernate.SessionFactory fromName(final String propertyGroupName) {
        return null;
    }
}
