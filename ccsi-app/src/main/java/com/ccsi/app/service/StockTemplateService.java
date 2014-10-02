package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.custom.StockTemplateServiceCustom;

/**
 * @author mbmartinez
 */
public interface StockTemplateService extends JpaRepository<StockTemplate, Long>, StockTemplateServiceCustom {

    List<StockTemplate> findByTenant_id(Long tenantId, Sort sort);
    StockTemplate findByTenantAndKeyword(Tenant tenant, String trackingNoOrKeyword);

}
