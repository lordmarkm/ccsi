package com.ccsi.commons.dto.tenant;

import java.math.BigDecimal;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TenantInfo extends BaseCcsiInfo {

    @NotBlank(message = "Tenant name can't be blank!")
    protected String name;

    @NotBlank(message = "Keyword can't be blank!")
    @Length(min = 5, max = 20, message = "Keywords must be between 5 and 20 characters in length")
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "Please choose an alphanumeric keyword with no spaces.")
    private String keyword;

    private String owner;
    private long recordCount;
    private long transactionCount;
    private BigDecimal pushCredits;

    public BigDecimal getPushCredits() {
        return pushCredits;
    }

    public void setPushCredits(BigDecimal pushCredits) {
        this.pushCredits = pushCredits;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(long transactionCount) {
        this.transactionCount = transactionCount;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

}
