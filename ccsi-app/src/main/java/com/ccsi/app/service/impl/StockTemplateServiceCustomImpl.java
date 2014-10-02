package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.service.StockTemplateService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.custom.StockTemplateServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.tenant.StockTemplateInfo;

/**
 * @author mbmartinez
 */
public class StockTemplateServiceCustomImpl extends MappingService<StockTemplate, StockTemplateInfo>
    implements StockTemplateServiceCustom {

    @Autowired
    private StockTemplateService service;

    @Autowired
    private TenantService tenantService;

    @Override
    public List<StockTemplateInfo> findInfoByTenantId(Long tenantId, Sort sort) {
        return toDto(service.findByTenant_id(tenantId, sort));
    }

    @Override
    public StockTemplateInfo saveInfo(Long tenantId, StockTemplateInfo templateInfo) {
        StockTemplate template = toEntity(templateInfo);
        template.setTenant(tenantService.findOne(tenantId));
        return toDto(service.save(template));
    }

}
