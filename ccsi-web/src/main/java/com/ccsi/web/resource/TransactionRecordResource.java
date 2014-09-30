package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TransactionRecordInfo;

@RestController
@RequestMapping("/txn/{tenantId}")
public class TransactionRecordResource extends GenericController {

    private static Logger LOG = LoggerFactory.getLogger(TransactionRecordResource.class);

    @Autowired
    private TransactionRecordService service;

    @RequestMapping(method = GET)
    public ResponseEntity<PageInfo<TransactionRecordInfo>> page(Principal principal,
            @PathVariable Long tenantId,
            @RequestParam int page,
            @RequestParam int count) {

        LOG.debug("Txn query. Principal={}, page={}, count={}, tenantId={}", name(principal), page, count, tenantId);

        PageRequest pageRequest = new PageRequest(page - 1, count, Direction.DESC, "transactionDate");
        PageInfo<TransactionRecordInfo> pageResponse = service.pageInfo(tenantId, pageRequest);

        return new ResponseEntity<>(pageResponse, OK);
    }

    @RequestMapping(value = "/{tenantRecordId}", method = GET)
    public ResponseEntity<PageInfo<TransactionRecordInfo>> pageByTenantRecord(Principal principal,
            @PathVariable Long tenantId,
            @PathVariable Long tenantRecordId,
            @RequestParam int page,
            @RequestParam int count) {

        LOG.debug("Txn query. Principal={}, page={}, count={}, tenantId={}, tenantRecordId={}",
                name(principal), page, count, tenantId, tenantRecordId);

        PageRequest pageRequest = new PageRequest(page - 1, count, Direction.DESC, "transactionDate");
        PageInfo<TransactionRecordInfo> pageResponse = service.pageInfo(tenantId, tenantRecordId, pageRequest);

        return new ResponseEntity<>(pageResponse, OK);
    }
}