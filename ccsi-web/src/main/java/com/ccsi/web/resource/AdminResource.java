package com.ccsi.web.resource;

import java.math.BigDecimal;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.TenantService;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static com.ccsi.app.util.BigDecimalUtil.*;
import static org.springframework.http.HttpStatus.*;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Transactional
public class AdminResource extends GenericController {

    private static Logger LOG = LoggerFactory.getLogger(AdminResource.class);

    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/credits/{tenantId}/{credits}", method = POST)
    public ResponseEntity<BigDecimal> giveCredits(Principal principal, @PathVariable Long tenantId, @PathVariable BigDecimal credits) {
        LOG.debug("Credits are being given. tenantId={}, credits={}, admin={}", tenantId, credits, name(principal));
        Tenant tenant = tenantService.findOne(tenantId);
        tenant.setPushCredits(zeroIfNull(tenant.getPushCredits()).add(credits));
        return new ResponseEntity<>(tenant.getPushCredits(), OK);
    }
    @RequestMapping(value = "/credits/{tenantId}/{credits}", method = DELETE)
    public ResponseEntity<BigDecimal> removeCredits(Principal principal, @PathVariable Long tenantId, @PathVariable BigDecimal credits) {
        LOG.debug("Credits are being taken away. tenantId={}, credits={}, admin={}", tenantId, credits, name(principal));
        Tenant tenant = tenantService.findOne(tenantId);
        tenant.setPushCredits(zeroIfNull(tenant.getPushCredits()).subtract(credits));
        return new ResponseEntity<>(tenant.getPushCredits(), OK);
    }
}
