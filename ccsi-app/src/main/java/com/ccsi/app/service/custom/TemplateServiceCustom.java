package com.ccsi.app.service.custom;

import java.util.List;

import com.ccsi.commons.dto.tenant.TemplateInfo;

/**
 * @author mbmartinez
 */
public interface TemplateServiceCustom {

    List<TemplateInfo> findInfoByTenantId(Long tenantId);
    TemplateInfo saveInfo(Long tenantId, TemplateInfo template);

}
