package com.ccsi.app.service.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.TenantInfo;

public class TenantServiceCustomImpl extends MappingService<Tenant, TenantInfo>
    implements TenantServiceCustom {

    @Autowired
    private TenantService service;

    @Override
    public List<TenantInfo> findByOwnerInfo(String name) {
        return toDto(service.findByOwner_username(name));
    }

}
