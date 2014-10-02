package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.custom.TenantRecordServiceCustom;

public interface TenantRecordService extends TenantRecordServiceCustom, JpaRepository<TenantRecord, Long>,
    QueryDslPredicateExecutor<TenantRecord> {

    TenantRecord findByTrackingNoIgnoreCaseAndTenant_id(String trackingNo, Long tenantId);
    List<TenantRecord> findByTenant_id(Long tenantId);
    Page<TenantRecord> findByTenant_id(Long tenantId, Pageable page);

}
