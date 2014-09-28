package com.ccsi.app.service.custom;

import java.util.List;

import com.ccsi.commons.dto.tenant.TenantInfo;

public interface TenantServiceCustom {
    TenantInfo findOneInfo(Long tenantId);
    List<TenantInfo> findByOwnerInfo(String name);
    TenantInfo saveInfo(String ownerName, TenantInfo tenant);
}
