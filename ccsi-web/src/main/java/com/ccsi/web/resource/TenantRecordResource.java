package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.service.TenantRecordService;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;

@RestController
@RequestMapping("/record/{tenantId}")
public class TenantRecordResource {

    @Autowired
    private TenantRecordService service;

    private static Logger LOG = LoggerFactory.getLogger(TenantRecordResource.class);

    @RequestMapping(method = GET)
    public ResponseEntity<PageInfo<TenantRecordInfo>> page(Principal principal,
            @PathVariable Long tenantId,
            @RequestParam int page,
            @RequestParam int count) {

        LOG.debug("User query. Principal={}, page={}, count={}", principal, page, count);

        PageRequest pageRequest = new PageRequest(page - 1, count, Direction.DESC, "lastUpdated");
        PageInfo<TenantRecordInfo> pageResponse = service.pageInfo(tenantId, pageRequest);

        return new ResponseEntity<>(pageResponse, OK);
    }
    
    @RequestMapping(method = POST)
    public ResponseEntity<TenantRecordInfo> save(@PathVariable Long tenantId,
            @RequestBody TenantRecordInfo record) {

        LOG.debug("Tenant record save request. tenant={}, record={}", tenantId, record);

        return new ResponseEntity<>(service.saveInfo(tenantId, record), OK);
    }
}
