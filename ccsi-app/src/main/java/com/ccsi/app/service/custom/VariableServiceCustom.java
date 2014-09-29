package com.ccsi.app.service.custom;

import java.util.List;

import com.ccsi.commons.dto.tenant.VariableInfo;

public interface VariableServiceCustom {

    List<VariableInfo> findInfoByTenantId(Long tenantId);
    VariableInfo saveInfo(VariableInfo template);

}
