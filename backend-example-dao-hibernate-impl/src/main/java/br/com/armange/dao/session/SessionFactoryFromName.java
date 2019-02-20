package br.com.armange.dao.session;

public interface SessionFactoryFromName {

    org.hibernate.SessionFactory fromName(final String name);
}
