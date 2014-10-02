package com.ccsi.commons.dto.tenant;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

/**
 * @author mbmartinez
 */
public class VariableInfo {

    private Long id;
    @NotEmpty(message = "Variable key can't be empty!")
    private String key;
    private String value;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", id)
            .append("key", key)
            .append("value", value)
            .toString();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}
