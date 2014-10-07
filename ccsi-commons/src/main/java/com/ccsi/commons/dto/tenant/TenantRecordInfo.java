package com.ccsi.commons.dto.tenant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.joda.time.LocalDateTime;
import org.springframework.core.style.ToStringCreator;

/**
 * @author mbmartinez
 */
public class TenantRecordInfo extends BaseCcsiInfo {

    private String trackingNo;
    @NotNull(message = "Record status must be defined. If you have not created any templates yet, please do so and try again.")
    private TemplateInfo status;
    private String customerName;
    private String transactionType;
    private LocalDateTime lastUpdated;
    @Pattern(regexp = "^639\\d{9}$", message = "Invalid broadcast number pattern (must match 639XXXXXXXXX)")
    private String broadcastNo;
    public String getBroadcastNo() {
        return broadcastNo;
    }

    public void setBroadcastNo(String broadcastNo) {
        this.broadcastNo = broadcastNo;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", id)
            .append("trackingNo", trackingNo)
            .append("status", status)
            .append("customerName", customerName)
            .append("txn type", transactionType)
            .append("last update", lastUpdated)
            .toString();
    }

    public String getTrackingNo() {
        return trackingNo;
    }
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
    public TemplateInfo getStatus() {
        return status;
    }
    public void setStatus(TemplateInfo status) {
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
