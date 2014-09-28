package com.ccsi.app.service.custom;

import java.util.List;

import com.ccsi.commons.dto.tenant.TemplateInfo;

public interface TemplateServiceCustom {

    List<TemplateInfo> findInfoByTenantId(Long tenantId);

}
