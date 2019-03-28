package br.com.armange.dao.artifacts;

import javax.persistence.Entity;

import br.com.armange.entity.BaseEntityGeneratedId;

@Entity
public class TestEntity extends BaseEntityGeneratedId<Long>{

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
