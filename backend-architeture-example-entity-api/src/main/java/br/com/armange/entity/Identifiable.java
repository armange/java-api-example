package br.com.armange.entity;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {

    T getId();
    
    void setId(T id);
}
