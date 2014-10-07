package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.baldy.commons.security.models.Account;
import com.baldy.commons.security.services.AccountService;
import com.ccsi.app.entity.QTenantRecord;
import com.ccsi.app.entity.QTransactionRecord;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.app.service.custom.TenantServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantInfo;

public class TenantServiceCustomImpl extends MappingService<Tenant, TenantInfo>
    implements TenantServiceCustom {

    @Autowired
    private TenantService service;

    @Autowired
    private TransactionRecordService txnRecordService;

    @Autowired
    private TenantRecordService tenantRecordService;

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

    @Override
    public PageInfo<TenantInfo> pageInfo(PageRequest pageRequest) {
        Page<Tenant> tenants = service.findAll(pageRequest);
        List<TenantInfo> tenantInfos = toDto(tenants);

        for (TenantInfo tenantInfo : tenantInfos) {
            tenantInfo.setTransactionCount(txnRecordService.count(QTransactionRecord.transactionRecord.tenant.id.eq(tenantInfo.getId())));
            tenantInfo.setRecordCount(tenantRecordService.count(QTenantRecord.tenantRecord.tenant.id.eq(tenantInfo.getId())));
        }

        PageInfo<TenantInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(tenantInfos);
        pageResponse.setTotal(tenants.getTotalElements());

        return pageResponse;
    }

}
