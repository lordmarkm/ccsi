package com.ccsi.web.security;

import java.security.Principal;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.TenantService;

/**
 * @author mbmartinez
 */
@Component("ccsiSecurityService")
public class CcsiSecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(CcsiSecurityService.class);

    @Autowired
    private TenantService tenantService;

    public boolean isOwner(Principal principal, Long tenantId) {
        try {
            String name = principal.getName();
            Tenant tenant = tenantService.findOne(tenantId);
            String owner = tenant.getOwner().getUsername();
            LOG.debug("Checking if owner. principal name={}, tenant={}, owner name={}", name, tenant.getName(), owner);
            return ObjectUtils.equals(name, owner);
        } catch (Exception e) {
            LOG.debug("Exception causes authentication failure. e={}", e.getMessage());
            return false;
        }
    }
}
