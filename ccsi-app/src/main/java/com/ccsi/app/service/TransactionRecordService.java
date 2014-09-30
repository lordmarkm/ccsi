package com.ccsi.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.custom.TransactionRecordServiceCustom;

/**
 * @author mbmartinez
 */
public interface TransactionRecordService extends TransactionRecordServiceCustom,
    JpaRepository<TransactionRecord, Long> {

    TransactionRecord findByMessageId(String message_id);
    Page<TransactionRecord> findByTenant_id(Long tenantId, Pageable pageRequest);
    Page<TransactionRecord> findByRecord_id(Long tenantRecordId, Pageable pageRequest);

}
