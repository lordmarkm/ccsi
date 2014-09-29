package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baldy.commons.security.models.Account;
import com.baldy.commons.security.services.AccountService;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.custom.TenantServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.TenantInfo;

public class TenantServiceCustomImpl extends MappingService<Tenant, TenantInfo>
    implements TenantServiceCustom {

    @Autowired
    private TenantService service;

    @Autowired
    private AccountService accounts;

    @Override
    public TenantInfo findOneInfo(Long tenantId) {
        return toDto(service.findOne(tenantId));
    }

    @Override
    public List<TenantInfo> findByOwnerInfo(String name) {
        return toDto(service.findByOwner_username(name));
    }

    @Override
    public TenantInfo saveInfo(String ownerName, TenantInfo tenantInfo) {
        Tenant tenant = toEntity(tenantInfo);
        Account account = accounts.findByUsername(ownerName);
        tenant.setOwner(account);
        return toDto(service.save(tenant));
    }

}
