package br.com.armange.dao;

public interface CountedPage<T> extends Page<T> {
    
    Long getPageCount();
    
    void setPageCount(Long pageCount);
}
