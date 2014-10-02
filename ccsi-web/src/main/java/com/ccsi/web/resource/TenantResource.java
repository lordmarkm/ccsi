package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.app.service.TenantService;
import com.ccsi.commons.dto.tenant.TenantInfo;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/tenants")
public class TenantResource extends GenericController {

    private static Logger LOG = LoggerFactory.getLogger(TenantResource.class);

    @Autowired
    private TenantService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TenantInfo>> findAllTenants(Principal principal) {
        LOG.info("Finding all tenants. owner={}", principal.getName());
        return new ResponseEntity<>(service.findByOwnerInfo(principal.getName()), OK);
    }

    @RequestMapping(value = "/{tenantId}", method = GET)
    public ResponseEntity<TenantInfo> findOneInfo(@PathVariable Long tenantId) {
        LOG.info("Finding single tenant. id={}", tenantId);
        return new ResponseEntity<>(service.findOneInfo(tenantId), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Object> save(Principal principal, @Valid @RequestBody TenantInfo tenant, BindingResult binding) {
        LOG.info("Save tenant request. tenant={}", tenant);
        if (binding.hasErrors()) {
            return new ResponseEntity<Object>(firstError(binding), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(service.saveInfo(principal.getName(), tenant), OK);
    }
}
