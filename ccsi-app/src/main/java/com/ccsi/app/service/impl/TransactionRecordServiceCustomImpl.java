package com.ccsi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.app.service.custom.TransactionRecordServiceCustom;
import com.ccsi.app.util.MappingService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TransactionRecordInfo;

/**
 * @author mbmartinez
 */
public class TransactionRecordServiceCustomImpl extends MappingService<TransactionRecord, TransactionRecordInfo>
    implements TransactionRecordServiceCustom {

    @Autowired
    private TransactionRecordService service;

    @Override
    public PageInfo<TransactionRecordInfo> pageInfo(PageRequest pageRequest) {
        Page<TransactionRecord> records = service.findAll(pageRequest);
        List<TransactionRecordInfo> userInfos = toDto(records);

        PageInfo<TransactionRecordInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(userInfos);
        pageResponse.setTotal(records.getTotalElements());

        return pageResponse;
    }

    @Override
    public PageInfo<TransactionRecordInfo> pageInfo(Long tenantId, PageRequest pageRequest) {
        Page<TransactionRecord> records = service.findByTenant_id(tenantId, pageRequest);
        List<TransactionRecordInfo> userInfos = toDto(records);

        PageInfo<TransactionRecordInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(userInfos);
        pageResponse.setTotal(records.getTotalElements());

        return pageResponse;
    }

    @Override
    public PageInfo<TransactionRecordInfo> pageInfo(Long tenantId,
            Long tenantRecordId, PageRequest pageRequest) {

        Page<TransactionRecord> records = service.findByRecord_id(tenantRecordId, pageRequest);
        List<TransactionRecordInfo> userInfos = toDto(records);

        PageInfo<TransactionRecordInfo> pageResponse = new PageInfo<>();
        pageResponse.setData(userInfos);
        pageResponse.setTotal(records.getTotalElements());

        return pageResponse;
    }

}
