package com.ccsi.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.baldy.commons.models.BaseEntity;

/**
 * @author mbmartinez
 */
@Entity(name = "TENANT_VARIABLE")
public class Variable extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "TENANT_ID", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private TenantRecord record;

    @Column(name = "VAR_KEY", nullable = false)
    private String key;

    @Column(name = "VAR_VALUE", nullable = false)
    private String value;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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

    public TenantRecord getRecord() {
        return record;
    }

    public void setRecord(TenantRecord record) {
        this.record = record;
    }
}
