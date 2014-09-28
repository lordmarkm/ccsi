package com.ccsi.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.baldy.commons.models.BaseBaldyEntity;
import com.baldy.commons.security.models.Account;

@Entity(name = "TENANT")
public class Tenant extends BaseBaldyEntity {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private Account owner;

    @Column(name = "KEYWORD", nullable = false)
    private String keyword;

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
