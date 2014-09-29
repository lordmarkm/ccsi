package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.custom.TenantRecordServiceCustom;

public interface TenantRecordService extends TenantRecordServiceCustom, JpaRepository<TenantRecord, Long> {

    TenantRecord findByTrackingNoAndTenant_id(String trackingNo, Long tenantId);
    List<TenantRecord> findByTenant_id(Long tenantId);
    Page<TenantRecord> findByTenant_id(Long tenantId, Pageable page);

}
