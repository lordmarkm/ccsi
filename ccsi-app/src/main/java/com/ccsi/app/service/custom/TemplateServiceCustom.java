package com.ccsi.app.service.custom;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.ccsi.commons.dto.tenant.TemplateInfo;

/**
 * @author mbmartinez
 */
public interface TemplateServiceCustom {

    List<TemplateInfo> findInfoByTenantId(Long tenantId, Sort sort);
    TemplateInfo saveInfo(Long tenantId, TemplateInfo template);

    /**
     * Try and delete this template.
     * @return the number of records still using this template (nonzero = delete fails)
     */
    long tryDelete(Long templateId);
}
