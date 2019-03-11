package br.com.armange.dao.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.deltaspike.core.api.config.ConfigResolver;

abstract class AbstractSessionFactoryFromName {
    protected final Map<String, org.hibernate.SessionFactory> sessionFactotyMap = new HashMap<>();
    protected final Map<String, String> allProperties = ConfigResolver.getAllProperties();
}
