package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccsi.app.entity.Variable;
import com.ccsi.app.service.VariableService;
import com.ccsi.app.service.custom.VariableServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.VariableInfo;

public class VariableServiceCustomImpl extends MappingService<Variable, VariableInfo>
    implements VariableServiceCustom {

    @Autowired
    private VariableService service;

    @Override
    public List<VariableInfo> findInfoByTenantId(Long tenantId) {
        return toDto(service.findByTenant_id(tenantId));
    }

    @Override
    public VariableInfo saveInfo(VariableInfo variableInfo) {
        Variable variable = toEntity(variableInfo);
        return toDto(service.save(variable));
    }

}
