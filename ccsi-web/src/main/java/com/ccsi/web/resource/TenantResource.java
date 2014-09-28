package com.ccsi.web.resource;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.service.TenantService;
import com.ccsi.commons.dto.tenant.TenantInfo;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/tenant")
public class TenantResource {

    private static Logger LOG = LoggerFactory.getLogger(TenantResource.class);

    @Autowired
    private TenantService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TenantInfo>> findAllTenants(Principal principal) {
        LOG.debug("Finding all tenants. owner={}", principal.getName());
        return new ResponseEntity<>(service.findByOwnerInfo(principal.getName()), OK);
    }

}
