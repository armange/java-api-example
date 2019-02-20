package br.com.armange.entity.node;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.armange.entity.BaseEntityGeneratedId;

@Entity
public class Node extends BaseEntityGeneratedId<Long> {

    private Long code;
    private Long limitCode;
    private String description;
    
    @OneToOne(mappedBy = "id")
    @Column(name = "parentId")
    private Node parentNode;
    
    @OneToMany
    @JoinColumn
    private List<Node> children;

    public Long getCode() {
        return code;
    }

    public void setCode(final Long code) {
        this.code = code;
    }

    public Long getLimitCode() {
        return limitCode;
    }

    public void setLimitCode(final Long limitCode) {
        this.limitCode = limitCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(final Node parentNode) {
        this.parentNode = parentNode;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(final List<Node> children) {
        this.children = children;
    }
}
