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
public class VariableResource {

    private static Logger LOG = LoggerFactory.getLogger(VariableResource.class);

    @Autowired
    private VariableService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<VariableInfo>> findByTenant(@PathVariable Long tenantId) {
        LOG.debug("Finding variables by tenant id. id={}", tenantId);
        return new ResponseEntity<>(service.findInfoByTenantId(tenantId, null), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<VariableInfo> save(@PathVariable Long tenantId,
            @Valid @RequestBody VariableInfo variable) {
        LOG.debug("Saving variable. tenant={}, variable={}", variable);
        return new ResponseEntity<>(service.saveInfo(tenantId, variable), OK);
    }

    //TODO need @PreAuthorized here for sure
    @RequestMapping(value = "/{variableId}", method = DELETE)
    public ResponseEntity<String> delete(Principal principal, @PathVariable Long tenantId, @PathVariable Long variableId) {
        LOG.debug("Delete variable request. tenant={}, variable={}", tenantId, variableId);
        service.delete(variableId);
        return new ResponseEntity<>("Ok", OK);
    }
}
