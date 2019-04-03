package br.com.armange.entity;

import java.util.Date;

public interface Auditable {

    Date getInsertedAt();
    
    void setInsertedAt(Date insertedAt);
    
    Date getUpdatedAt();
    
    void setUpdatedAt(Date updatedAt);
}
