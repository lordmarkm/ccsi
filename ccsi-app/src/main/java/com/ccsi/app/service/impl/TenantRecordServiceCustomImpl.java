package com.ccsi.app.service.impl;

import static com.ccsi.app.entity.QTenantRecord.tenantRecord;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ccsi.app.entity.QTenantRecord;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.custom.TenantRecordServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;
import com.mysema.query.types.expr.BooleanExpression;

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
            String candidate = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
            while (service.findByTrackingNoIgnoreCaseAndTenant_id(candidate, tenantId) != null) {
                candidate = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
            }
            record.setTrackingNo(candidate);
        }

        return toDto(service.save(record));
    }

    @Override
    public PageInfo<TenantRecordInfo> pageInfo(Long tenantId, PageRequest pageRequest, Map<String, String> optionalParams) {
        //Page<TenantRecord> records = service.findByTenant_id(tenantId, pageRequest);

        String trackingNo = optionalParams.get("trackingNo");
        String status = optionalParams.get("status");
        String customerName = optionalParams.get("customerName");
        String transactionType = optionalParams.get("transactionType");

        BooleanExpression predicate = QTenantRecord.tenantRecord.tenant.id.eq(tenantId);;
        if (null != trackingNo) {
            predicate = predicate.and(tenantRecord.trackingNo.eq(trackingNo));
        } else {
            if (null != status) {
                predicate = predicate.and(tenantRecord.status.status.eq(status));
            }
            if (null != customerName) {
                predicate = predicate.and(tenantRecord.customerName.eq(customerName));
            }
            if (null != transactionType) {
                predicate = predicate.and(tenantRecord.transactionType.eq(transactionType));
            }
        }

        Page<TenantRecord> records = service.findAll(predicate, pageRequest);

        List<TenantRecordInfo> userInfos = toDto(records);

        PageInfo<TenantRecordInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(userInfos);
        pageResponse.setTotal(records.getTotalElements());

        return pageResponse;
    }

}
