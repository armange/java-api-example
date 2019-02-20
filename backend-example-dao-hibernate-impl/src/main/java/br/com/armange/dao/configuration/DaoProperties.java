package br.com.armange.dao.configuration;

import org.apache.deltaspike.core.api.config.Configuration;

@Configuration(prefix=DaoProperties.PREFIX)
public interface DaoProperties {
    public static final String PREFIX = "br.com.armange.dao";
}
