package br.com.armange.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntityGeneratedId<T extends Serializable> implements 
    Identifiable<T>,
    //This type will be optimized on next versions.
    Versionable<Date> {

    @GeneratedValue
    @Id
    private T id;
    
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Access(AccessType.FIELD)
    private Date version;
    
    @Override
    public T getId() {
        return id;
    }
    
    @Override
    public void setId(final T id) {
        this.id = id;
    }
    
    @Override
    public Date getVersion() {
        return version;
    }
    
    @Override
    public void setVersion(final Date version) {
        this.version = version;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        BaseEntityGeneratedId other = (BaseEntityGeneratedId) obj;
        
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        
        return true;
    }
}
