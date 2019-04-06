package br.com.armange.dao;

import java.util.List;

public class PageImpl<T> implements Page<T> {

    private List<T> rows;
    private int pageNumber;
    private int pageStart;
    private int pageSize;
    
    public PageImpl() {}
    
    public PageImpl(final List<T> rows, 
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
    public void setRows(final List<T> rows) {
        this.rows = rows;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }
    
    @Override
    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public int getPageStart() {
        return pageStart;
    }
    
    @Override
    public void setPageStart(final int pageStart) {
        this.pageStart = pageStart;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
    
    @Override
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }
}
