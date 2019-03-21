package br.com.armange.dao;

import br.com.armange.dao.session.NamedSessionFactory;

public class OrmServerImpl implements OrmServer {

    private final String name;
    
    public OrmServerImpl(final String name) {
        this.name = name;
    }
    
    @Override
    public String serverName() {
        return name;
    }
    
    @Override
    public Object getOrmImpl() {
        return NamedSessionFactory.fromName(name);
    }
}
