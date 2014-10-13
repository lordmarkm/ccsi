package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.service.VariableService;
import com.ccsi.commons.dto.tenant.VariableInfo;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/variable/{tenantId}")
@PreAuthorize("@ccsiSecurityService.isOwner(#principal, #tenantId)")
public class VariableResource {

    private static Logger LOG = LoggerFactory.getLogger(VariableResource.class);

    @Autowired
    private VariableService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<VariableInfo>> findByTenant(Principal principal, @PathVariable Long tenantId) {
        LOG.debug("Finding variables by tenant id. id={}", tenantId);
        return new ResponseEntity<>(service.findInfoByTenantId(tenantId, null), OK);
    }

    @RequestMapping(value = "/{recordId}", method = GET)
    public ResponseEntity<List<VariableInfo>> findByRecordId(Principal principal, @PathVariable Long tenantId, @PathVariable Long recordId) {
        LOG.debug("Finding variables by record id. tenantId={}, recordId={}", tenantId, recordId);
        return new ResponseEntity<>(service.findInfoByRecordId(tenantId, recordId, null), OK);
    }

    @RequestMapping(value = "/{recordId}", method = POST)
    public ResponseEntity<VariableInfo> saveRecordVariable(Principal principal, @PathVariable Long tenantId, @PathVariable Long recordId,
            @Valid @RequestBody VariableInfo variable) {
        LOG.debug("Saving record variable. tenantId={}, recordId={}, var={}", tenantId, recordId, variable);
        return new ResponseEntity<>(service.saveInfo(tenantId, recordId, variable), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<VariableInfo> saveTenantVariable(Principal principal, @PathVariable Long tenantId,
            @Valid @RequestBody VariableInfo variable) {
        LOG.debug("Saving variable. tenant={}, variable={}", variable);
        return new ResponseEntity<>(service.saveInfo(tenantId, null, variable), OK);
    }

    @RequestMapping(value = "/{variableId}", method = DELETE)
    public ResponseEntity<String> delete(Principal principal, @PathVariable Long tenantId, @PathVariable Long variableId) {
        LOG.debug("Delete variable request. tenant={}, variable={}", tenantId, variableId);
        service.delete(variableId);
        return new ResponseEntity<>("Ok", OK);
    }
}
