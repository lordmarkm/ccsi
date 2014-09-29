package com.ccsi.commons.dto.tenant;

/**
 * @author mbmartinez
 */
public class BaseCcsiInfo {

    protected Long id;
    protected String name;
    protected String description;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
