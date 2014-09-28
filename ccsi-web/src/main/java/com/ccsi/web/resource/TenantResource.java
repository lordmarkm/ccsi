package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.service.TenantService;
import com.ccsi.commons.dto.tenant.TenantInfo;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/tenants")
public class TenantResource {

    private static Logger LOG = LoggerFactory.getLogger(TenantResource.class);

    @Autowired
    private TenantService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TenantInfo>> findAllTenants(Principal principal) {
        LOG.debug("Finding all tenants. owner={}", principal.getName());
        return new ResponseEntity<>(service.findByOwnerInfo(principal.getName()), OK);
    }

    @RequestMapping(value = "/{tenantId}", method = GET)
    public ResponseEntity<TenantInfo> findOneInfo(@PathVariable Long tenantId) {
        LOG.debug("Finding single tenant. id={}", tenantId);
        return new ResponseEntity<>(service.findOneInfo(tenantId), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<TenantInfo> save(Principal principal, @RequestBody TenantInfo tenant) {
        LOG.debug("Saving tenant. tenant={}", tenant);
        return new ResponseEntity<>(service.saveInfo(principal.getName(), tenant), OK);
    }
}
