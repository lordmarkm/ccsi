package com.ccsi.app.entity;

import javax.annotation.Untainted;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.baldy.commons.models.BaseEntity;

/**
 * @author mbmartinez
 */
@Entity(name = "STOCK_TEMPLATE")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"TENANT_ID", "KEYWORD"})})
public class StockTemplate extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "TENANT_ID", nullable = false)
    private Tenant tenant;

    @Column(name = "KEYWORD", nullable = false)
    private String keyword;

    @Column(name = "REPLY", nullable = false)
    private String reply;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
