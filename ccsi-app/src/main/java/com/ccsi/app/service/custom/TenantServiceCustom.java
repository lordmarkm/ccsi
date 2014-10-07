package com.ccsi.app.service.custom;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantInfo;

/**
 * @author mbmartinez
 */
public interface TenantServiceCustom {

    PageInfo<TenantInfo> pageInfo(PageRequest pageRequest);
    TenantInfo findOneInfo(Long tenantId);
    List<TenantInfo> findByOwnerInfo(String name);
    TenantInfo saveInfo(String ownerName, TenantInfo tenant);

}
