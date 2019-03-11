package br.com.armange.entity;

public interface Identifiable<T> {

    T getId();
    
    void setId(T id);
}
