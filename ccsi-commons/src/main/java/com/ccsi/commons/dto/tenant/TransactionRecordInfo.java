package com.ccsi.commons.dto.tenant;

import java.math.BigDecimal;

import org.joda.time.LocalDateTime;


/**
 * @author mbmartinez
 */
public class TransactionRecordInfo {

    private TenantRecordInfo record;
    private String mobileNumber;
    private String incomingMessage;
    private String outgoingMessage;
    private LocalDateTime transactionDate;
    private BigDecimal cost;
    private String messageId;
    private boolean delivered;

    public TenantRecordInfo getRecord() {
        return record;
    }
    public void setRecord(TenantRecordInfo record) {
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
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public boolean isDelivered() {
        return delivered;
    }
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

}
