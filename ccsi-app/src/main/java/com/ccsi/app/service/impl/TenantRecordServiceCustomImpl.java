package com.ccsi.app.service.impl;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.custom.TenantRecordServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;

public class TenantRecordServiceCustomImpl extends MappingService<TenantRecord, TenantRecordInfo>
    implements TenantRecordServiceCustom {

    @Autowired
    private TenantRecordService service;

    @Autowired
    private TenantService tenantService;

    @Override
    public TenantRecordInfo findOneInfo(Long tenantRecordId) {
        return toDto(service.findOne(tenantRecordId));
    }

    @Override
    public List<TenantRecordInfo> findInfoByTenantId(Long tenantId) {
        return toDto(service.findByTenant_id(tenantId));
    }

    @Override
    public TenantRecordInfo saveInfo(Long tenantId, TenantRecordInfo recordInfo) {
        TenantRecord record = toEntity(recordInfo);

        record.setLastUpdated(LocalDateTime.now());

        Tenant tenant = tenantService.findOne(tenantId);
        record.setTenant(tenant);

        //TODO
        if (null == record.getTrackingNo()) {
            String candidate = RandomStringUtils.randomAlphanumeric(4);
            while (service.findByTrackingNoAndTenant_id(candidate, tenantId) != null) {
                candidate = RandomStringUtils.randomAlphanumeric(4);
            }
            record.setTrackingNo(candidate);
        }

        return toDto(service.save(record));
    }

    @Override
    public PageInfo<TenantRecordInfo> pageInfo(Long tenantId, PageRequest pageRequest) {
        Page<TenantRecord> records = service.findByTenant_id(tenantId, pageRequest);
        List<TenantRecordInfo> userInfos = toDto(records);

        PageInfo<TenantRecordInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(userInfos);
        pageResponse.setTotal(records.getTotalElements());

        return pageResponse;
    }

}
