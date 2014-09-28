package com.ccsi.app.service.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccsi.app.entity.Template;
import com.ccsi.app.service.TemplateService;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.TemplateInfo;

public class TemplateServiceCustomImpl extends MappingService<Template, TemplateInfo>
    implements TemplateServiceCustom {

    @Autowired
    private TemplateService service;

    @Override
    public List<TemplateInfo> findInfoByTenantId(Long tenantId) {
        return toDto(service.findByTenant_id(tenantId));
    }

}
