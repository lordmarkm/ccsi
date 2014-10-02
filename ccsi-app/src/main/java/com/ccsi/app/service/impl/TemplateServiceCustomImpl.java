package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.ccsi.app.entity.QTenantRecord;
import com.ccsi.app.entity.Template;
import com.ccsi.app.service.TemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.custom.TemplateServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.TemplateInfo;

public class TemplateServiceCustomImpl extends MappingService<Template, TemplateInfo>
    implements TemplateServiceCustom {

    @Autowired
    private TemplateService service;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantRecordService tenantRecordService;

    @Override
    public List<TemplateInfo> findInfoByTenantId(Long tenantId, Sort sort) {
        return toDto(service.findByTenant_id(tenantId, sort));
    }

    @Override
    public TemplateInfo saveInfo(Long tenantId, TemplateInfo templateInfo) {
        Template template = toEntity(templateInfo);
        template.setTenant(tenantService.findOne(tenantId));
        return toDto(service.save(template));
    }

    @Override
    public long tryDelete(Long templateId) {
        long records = tenantRecordService.count(QTenantRecord.tenantRecord.status.id.eq(templateId));
        if (0 == records) {
            service.delete(templateId);
        }
        return records;
    }

}
