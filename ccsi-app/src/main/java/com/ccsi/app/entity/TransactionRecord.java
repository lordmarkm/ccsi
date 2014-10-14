package com.ccsi.app.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.baldy.commons.models.BaseEntity;
import com.ccsi.commons.reference.Network;

@Entity(name = "TRANSACTION_RECORD")
public class TransactionRecord extends BaseEntity {

    /**
     * Saving this on top of TenantRecord for cases where Tenant is determined but TenantRecord is not.
     */
    @ManyToOne
    @JoinColumn(name = "TENANT_ID")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private TenantRecord record;

    @Column(name = "MOBILE_NO")
    private String mobileNumber;

    @Column(name = "INCOMING_MSG")
    private String incomingMessage;

    @Column(name = "OUTGOING_MSG")
    private String outgoingMessage;

    @Column(name = "TXN_DATE")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime transactionDate;

    @Column(name = "COST")
    private BigDecimal cost;

    @Column(name = "REQ_ID")
    private String requestId;

    @Column(name = "MSG_ID")
    private String messageId;

    @Column(name = "DELIVERED")
    private boolean delivered = false;

    @Column(name = "NETWORK")
    @Enumerated(EnumType.STRING)
    private Network network;

    @Column(name = "NETWORK_DESCRIPTION")
    private String networkDescription;

    public TenantRecord getRecord() {
        return record;
    }

    public void setRecord(TenantRecord record) {
        this.record = record;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getIncomingMessage() {
        return incomingMessage;
    }

    public void setIncomingMessage(String incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    public String getOutgoingMessage() {
        return outgoingMessage;
    }

    public void setOutgoingMessage(String outgoingMessage) {
        this.outgoingMessage = outgoingMessage;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getNetworkDescription() {
        return networkDescription;
    }

    public void setNetworkDescription(String networkDescription) {
        this.networkDescription = networkDescription;
    }

}
