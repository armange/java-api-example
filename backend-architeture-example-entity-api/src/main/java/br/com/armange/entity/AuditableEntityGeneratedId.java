package br.com.armange.entity;

import java.io.Serializable;
import java.util.Date;

public class AuditableEntityGeneratedId<T extends Serializable> extends BaseEntityGeneratedId<T> implements Auditable {

    private Date insertedAt;
    
    private Date updatedAt;
    
    @Override
    public Date getInsertedAt() {
        return insertedAt;
    }
    
    @Override
    public void setInsertedAt(Date insertedAt) {
        this.insertedAt = insertedAt;
    }
    
    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
