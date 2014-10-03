package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.ccsi.app.entity.Variable;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.VariableService;
import com.ccsi.app.service.custom.VariableServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.VariableInfo;

public class VariableServiceCustomImpl extends MappingService<Variable, VariableInfo>
    implements VariableServiceCustom {

    @Autowired
    private VariableService service;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private TenantRecordService recordService;

    @Override
    public List<VariableInfo> findInfoByTenantId(Long tenantId, Sort sort) {
        return toDto(service.findByTenant_idAndRecord_id(tenantId, null, sort));
    }

    @Override
    public List<VariableInfo> findInfoByRecordId(Long tenantId, Long recordId, Sort sort) {
        return toDto(service.findByTenant_idAndRecord_id(tenantId, recordId, sort));
    }

    @Override
    public VariableInfo saveInfo(Long tenantId, Long recordId, VariableInfo variableInfo) {
        Variable variable = toEntity(variableInfo);
        variable.setTenant(tenantService.findOne(tenantId));
        if (null != recordId) {
            variable.setRecord(recordService.findOne(recordId));
        }
        return toDto(service.save(variable));
    }

}
