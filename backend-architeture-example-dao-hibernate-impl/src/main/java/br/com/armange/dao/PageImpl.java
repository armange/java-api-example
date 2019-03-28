package br.com.armange.dao;

import java.util.List;

public class PageImpl<T> implements Page<T> {

    private final List<T> rows;
    private final int pageNumber;
    private final int pageStart;
    private final int pageSize;
    
    PageImpl(final List<T> rows, 
            final int pageNumber, 
            final int pageStart, 
            final int pageSize) {
        this.rows = rows;
        this.pageNumber = pageNumber;
        this.pageStart = pageStart;
        this.pageSize = pageSize;
    }

    @Override
    public List<T> getRows() {
        return rows;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageStart() {
        return pageStart;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
