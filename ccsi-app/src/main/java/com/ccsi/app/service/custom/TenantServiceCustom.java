package com.ccsi.app.service.custom;

import java.util.List;

import com.ccsi.commons.dto.tenant.TenantInfo;

public interface TenantServiceCustom {
    List<TenantInfo> findByOwnerInfo(String name);
}
