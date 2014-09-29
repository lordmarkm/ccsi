package com.ccsi.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.baldy.commons.models.BaseEntity;

@Entity(name = "TENANT_RECORD")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"TENANT_ID", "TRACKING_NO"})})
public class TenantRecord extends BaseEntity {

    @Column(name = "TRACKING_NO", nullable = false)
    private String trackingNo;

    @ManyToOne
    @JoinColumn(name = "TENANT_ID", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID", nullable = false)
    private Template status;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "TXN_TYPE")
    private String transactionType;

    @Column(name = "LAST_UPDATE")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lastUpdated;

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Template getStatus() {
        return status;
    }

    public void setStatus(Template status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
