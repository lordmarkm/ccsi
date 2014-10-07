package com.ccsi.app.service.impl;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.exception.ReservedWordCollisionException;
import com.ccsi.app.reference.ReservedWord;
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

    private static Logger LOG = LoggerFactory.getLogger(StockTemplateServiceCustomImpl.class);

    @Autowired
    private StockTemplateService service;

    @Autowired
    private TenantService tenantService;

    @Override
    public List<StockTemplateInfo> findInfoByTenantId(Long tenantId, Sort sort) {
        return toDto(service.findByTenant_id(tenantId, sort));
    }

    @Override
    public StockTemplateInfo saveInfo(Long tenantId, StockTemplateInfo templateInfo) throws ReservedWordCollisionException {
        StockTemplate template = toEntity(templateInfo);

        for (ReservedWord r : ReservedWord.values()) {
            String keyword = template.getKeyword().toUpperCase();
            String reservedKeyword = r.getKeyword().trim();
            LOG.debug("Checking reserved word collision. keyword={}, reserved keyword={}", keyword, reservedKeyword);
            if (ObjectUtils.equals(keyword, reservedKeyword)) {
                throw new ReservedWordCollisionException("Sadly, " + template.getKeyword() + " is a reserved word. Please see the help files for more information.");
            }
        }

        template.setTenant(tenantService.findOne(tenantId));
        return toDto(service.save(template));
    }

}
