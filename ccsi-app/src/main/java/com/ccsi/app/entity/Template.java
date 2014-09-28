package com.ccsi.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.baldy.commons.models.BaseEntity;

@Entity(name = "TEMPLATE")
public class Template extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "TENANT_ID", nullable = false)
    private Tenant tenant;

    @Column(name = "STRING", nullable = false)
    private String templateString;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

}
