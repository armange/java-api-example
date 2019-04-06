package br.com.armange.entity;

public interface Versionable<T> {

    T getVersion();
    
    void setVersion(T version);
}
