package com.ccsi.app.service.custom;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;

/**
 * @author mbmartinez
 */
public interface TenantRecordServiceCustom {

    TenantRecordInfo findOneInfo(Long tenantRecordId);
    List<TenantRecordInfo> findInfoByTenantId(Long tenantId);
    PageInfo<TenantRecordInfo> pageInfo(Long tenantId, PageRequest pageRequest, Map<String, String> optionalParams);
    TenantRecordInfo saveInfo(Long tenantId, TenantRecordInfo record);

    /**
     * ..ForBroadcast means TenantRecord.broadcastNo can't be null.
     * @return List of tenantRecords matching the criteria in optionalParmas AND broadcastNo != null
     */
    List<TenantRecord> findAllByParams(Long tenantId, Map<String, String> optionalParams);
}
