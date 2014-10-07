package com.ccsi.app.service.custom;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.ccsi.app.exception.ReservedWordCollisionException;
import com.ccsi.commons.dto.tenant.StockTemplateInfo;

/**
 * @author mbmartinez
 */
public interface StockTemplateServiceCustom {

    List<StockTemplateInfo> findInfoByTenantId(Long tenantId, Sort sort);
    StockTemplateInfo saveInfo(Long tenantId, StockTemplateInfo template) throws ReservedWordCollisionException;

}
