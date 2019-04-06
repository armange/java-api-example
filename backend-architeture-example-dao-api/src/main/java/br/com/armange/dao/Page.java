package br.com.armange.dao;

import java.util.List;

public interface Page<T> {

    List<T> getRows();
    
    void setRows(List<T> rows);
    
    int getPageNumber();
    
    void setPageNumber(int pageNumber);
    
    int getPageStart();
    
    void setPageStart(int pageStart);
    
    int getPageSize();
    
    void setPageSize(int pageSize);
}
