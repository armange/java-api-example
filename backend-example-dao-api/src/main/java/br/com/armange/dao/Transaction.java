package br.com.armange.dao;

public interface Transaction {

    void commit();
    
    void rollback();
}
