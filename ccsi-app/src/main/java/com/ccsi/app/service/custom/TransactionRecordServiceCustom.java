package com.ccsi.app.service.custom;

import org.springframework.data.domain.PageRequest;

import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TransactionRecordInfo;

/**
 * @author mbmartinez
 */
public interface TransactionRecordServiceCustom {

    PageInfo<TransactionRecordInfo> pageInfo(PageRequest pageRequest);
    PageInfo<TransactionRecordInfo> pageInfo(Long tenantId, PageRequest pageRequest);
    PageInfo<TransactionRecordInfo> pageInfo(Long tenantId,
            Long tenantRecordId, PageRequest pageRequest);

}
