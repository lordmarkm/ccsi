package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.PageInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/record/{tenantId}")
@PreAuthorize("@ccsiSecurityService.isOwner(#principal, #tenantId)")
public class TenantRecordResource extends GenericController {

    @Autowired
    private TenantRecordService service;

    private static Logger LOG = LoggerFactory.getLogger(TenantRecordResource.class);

    @RequestMapping(method = GET)
    public ResponseEntity<PageInfo<TenantRecordInfo>> page(Principal principal,
            @PathVariable Long tenantId,
            @RequestParam int page,
            @RequestParam int count,

            //Optional filtering params
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String trackingNo,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String transactionType,

            //This method is also used by BroadcastController, which will set this to true
            @RequestParam(required = false) String requireBroadcastNo,

            //Will be "true" if it's the navbar making the search request
            @RequestParam(required = false) String navbar) {

        LOG.debug("User query. Principal={}, page={}, count={}", name(principal), page, count);

        Map<String, String> optionalParams = Maps.newHashMap();
        optionalParams.put("status", StringUtils.trimToNull(status));
        optionalParams.put("trackingNo", StringUtils.trimToNull(trackingNo));
        optionalParams.put("customerName", StringUtils.trimToNull(customerName));
        optionalParams.put("transactionType", StringUtils.trimToNull(transactionType));
        optionalParams.put("requireBroadcastNo", requireBroadcastNo);
        optionalParams.put("navbar", navbar);
        PageRequest pageRequest = new PageRequest(page - 1, count, Direction.DESC, "lastUpdated");
        PageInfo<TenantRecordInfo> pageResponse = service.pageInfo(tenantId, pageRequest, optionalParams);

        return new ResponseEntity<>(pageResponse, OK);
    }

    /**
     * Batch update
     **/
    @RequestMapping(method = POST, params="newStatus")
    public ResponseEntity<GenericHttpResponse> batchUpdate(Principal principal,
            @PathVariable Long tenantId,

            //Optional filtering params
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String trackingNo,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String transactionType,

            //This status will be set on all records that match the above params
            @RequestParam String newStatus) {

        LOG.debug("Batch update request. Principal={}", name(principal));

        Map<String, String> optionalParams = Maps.newHashMap();
        optionalParams.put("status", StringUtils.trimToNull(status));
        optionalParams.put("trackingNo", StringUtils.trimToNull(trackingNo));
        optionalParams.put("customerName", StringUtils.trimToNull(customerName));
        optionalParams.put("transactionType", StringUtils.trimToNull(transactionType));

        LOG.debug("About to do batch updatd. new status={}, filters={}", newStatus, optionalParams);
        int updated = service.batchUpdate(tenantId, optionalParams, newStatus);

        return new ResponseEntity<>(new GenericHttpResponse(OK.name(), String.valueOf(updated)), OK);
    }

    @RequestMapping(value = "/{tenantRecordId}", method = GET)
    public ResponseEntity<TenantRecordInfo> findOne(Principal principal, @PathVariable Long tenantId, @PathVariable Long tenantRecordId) {
        LOG.debug("Tenant record view request. tenant={}, record={}", tenantId, tenantRecordId);
        return new ResponseEntity<>(service.findOneInfo(tenantRecordId), OK);
    }

    @RequestMapping(value = "/{tenantRecordId}", method = DELETE)
    @PreAuthorize("@ccsiSecurityService.isOwner(#principal, #tenantId)")
    public ResponseEntity<TenantRecordInfo> deleteOne(Principal principal, @PathVariable Long tenantId, @PathVariable Long tenantRecordId) {
        LOG.debug("Tenant record delete request. tenant={}, record={}", tenantId, tenantRecordId);
        return new ResponseEntity<>(service.softDelete(tenantRecordId), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Object> save(Principal principal, @PathVariable Long tenantId,
            @Valid @RequestBody TenantRecordInfo record, BindingResult binding) {

        LOG.debug("Tenant record save request. tenant={}, record={}", tenantId, record);

        if (binding.hasErrors()) {
            return new ResponseEntity<Object>(firstError(binding), HttpStatus.BAD_REQUEST);
        }
        
        //Catch duplicate trackingNo save attempts
        try {
            return new ResponseEntity<Object>(service.saveInfo(tenantId, record), OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>("Duplicate tracking number", HttpStatus.BAD_REQUEST);
        }
    }
}
