package com.crud.model.base;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseModel implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
	protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BaseModel other = (BaseModel) obj;
        return Objects.equals(id, other.id);
    }
}