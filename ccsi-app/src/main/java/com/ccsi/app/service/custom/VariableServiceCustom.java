package com.ccsi.app.service.custom;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.ccsi.commons.dto.tenant.VariableInfo;

public interface VariableServiceCustom {

    List<VariableInfo> findInfoByTenantId(Long tenantId, Sort sort);
    VariableInfo saveInfo(Long tenantId, VariableInfo variable);

}
