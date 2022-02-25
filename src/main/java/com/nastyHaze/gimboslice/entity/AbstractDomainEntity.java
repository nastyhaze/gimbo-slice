package com.nastyHaze.gimboslice.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract Entity class to provide id column & generation strategy to data Entities.
 */
@MappedSuperclass
public class AbstractDomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    public AbstractDomainEntity() {
        super();
    }

    public AbstractDomainEntity(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDomainEntity that = (AbstractDomainEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AbstractDomainEntity{" +
                "id=" + id +
                '}';
    }
}
