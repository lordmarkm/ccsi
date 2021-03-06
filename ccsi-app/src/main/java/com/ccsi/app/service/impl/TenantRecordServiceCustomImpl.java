package com.ccsi.app.service.impl;

import static com.ccsi.app.entity.QTenantRecord.tenantRecord;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.ccsi.app.entity.QTenantRecord;
import com.ccsi.app.entity.Template;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.TemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.custom.TenantRecordServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;
import com.mysema.query.types.expr.BooleanExpression;

public class TenantRecordServiceCustomImpl extends MappingService<TenantRecord, TenantRecordInfo>
    implements TenantRecordServiceCustom {

    private static Logger LOG = LoggerFactory.getLogger(TenantRecordServiceCustomImpl.class);

    @Autowired
    private TenantRecordService service;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TemplateService templateService;

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
        } else {
            record.setTrackingNo(record.getTrackingNo().toUpperCase());
        }

        return toDto(service.save(record));
    }

    @Override
    public PageInfo<TenantRecordInfo> pageInfo(Long tenantId, PageRequest pageRequest, Map<String, String> optionalParams) {

        BooleanExpression predicate;
        if (optionalParams.get("navbar") != null) {
            predicate = prepareOrFiltrationQuery(tenantId, optionalParams);
        } else {
            predicate = prepareAndFiltrationQuery(tenantId, optionalParams);
        }

        Page<TenantRecord> records = service.findAll(predicate, pageRequest);

        List<TenantRecordInfo> userInfos = toDto(records);

        PageInfo<TenantRecordInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(userInfos);
        pageResponse.setTotal(records.getTotalElements());

        return pageResponse;
    }

    @Override
    public List<TenantRecord> findAllByParams(Long tenantId, Map<String, String> optionalParams) {
        BooleanExpression query = prepareAndFiltrationQuery(tenantId, optionalParams);
        return (List<TenantRecord>) service.findAll(query);
    }

    @Override
    public int batchUpdate(Long tenantId, Map<String, String> optionalParams, String newStatus) {
        BooleanExpression query = prepareAndFiltrationQuery(tenantId, optionalParams);
        Template status = templateService.findByTenant_idAndStatus(tenantId, newStatus);
        Iterable<TenantRecord> records = service.findAll(query);
        for (TenantRecord record : records) {
            record.setStatus(status);
        }
        return service.save(records).size();
    }

    private BooleanExpression prepareOrFiltrationQuery(Long tenantId, Map<String, String> optionalParams) {
        LOG.debug("Doing relaxed filtration query. params={}", optionalParams);
        String trackingNo = optionalParams.get("trackingNo");
        String customerName = optionalParams.get("customerName");
        String transactionType = optionalParams.get("transactionType");

        BooleanExpression idPredicate = QTenantRecord.tenantRecord.tenant.id.eq(tenantId);;
        BooleanExpression paramsPredicate = BooleanExpression.anyOf(
            tenantRecord.trackingNo.startsWithIgnoreCase(trackingNo),
            tenantRecord.customerName.startsWithIgnoreCase(customerName),
            tenantRecord.transactionType.startsWithIgnoreCase(transactionType)
        );

        //don't show deleted records
        idPredicate = idPredicate.and(tenantRecord.deleted.isFalse());
        BooleanExpression predicate = idPredicate.and(paramsPredicate);
        LOG.debug("Query prepared. query={}", predicate);

        return predicate;
    }

    private BooleanExpression prepareAndFiltrationQuery(Long tenantId, Map<String, String> optionalParams) {
        String trackingNo = optionalParams.get("trackingNo");
        String status = optionalParams.get("status");
        String customerName = optionalParams.get("customerName");
        String transactionType = optionalParams.get("transactionType");

        BooleanExpression predicate = QTenantRecord.tenantRecord.tenant.id.eq(tenantId);;
        if (null != trackingNo) {
            predicate = predicate.and(tenantRecord.trackingNo.startsWithIgnoreCase(trackingNo));
        }
        if (null != status) {
            predicate = predicate.and(tenantRecord.status.status.eq(status));
        }
        if (null != customerName) {
            predicate = predicate.and(tenantRecord.customerName.startsWithIgnoreCase(customerName));
        }
        if (null != transactionType) {
            predicate = predicate.and(tenantRecord.transactionType.eq(transactionType));
        }

        //broadcast no. filter
        if (null != optionalParams.get("requireBroadcastNo")) {
            predicate = predicate.and(tenantRecord.broadcastNo.isNotNull());
        }

        //don't show deleted records
        predicate = predicate.and(tenantRecord.deleted.isFalse());
        LOG.debug("Query prepared. query={}", predicate);

        return predicate;
    }

    @Override
    @Transactional
    public TenantRecordInfo softDelete(Long tenantRecordId) {
        TenantRecord record = service.findOne(tenantRecordId);
        record.setDeleted(true);
        return toDto(record);
    }

}
