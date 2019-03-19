package br.com.armange.dao;

import java.util.List;

public interface Page<T> {

    List<T> getRows();
    
    int getPageNumber();
    
    int getPageStart();
    
    int getPageSize();
}
