package com.ccsi.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.baldy.commons.models.BaseEntity;

/*
 * Force {@link StockTemplate} to create its own table.
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

/**
 * @author mbmartinez
 */
@Entity(name = "TEMPLATE")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"TENANT_ID", "STATUS"})})
public class Template extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "TENANT_ID", nullable = false)
    private Tenant tenant;

    @Column(name = "STATUS", nullable = false)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
