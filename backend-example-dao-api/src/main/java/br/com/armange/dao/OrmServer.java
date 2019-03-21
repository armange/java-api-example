package br.com.armange.dao;

public interface OrmServer {

    String serverName();
    
    Object getOrmImpl();
}
