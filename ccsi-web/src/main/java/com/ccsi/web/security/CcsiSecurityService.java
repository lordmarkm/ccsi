package com.ccsi.web.security;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author mbmartinez
 */
@Component("ccsiSecurityService")
public class CcsiSecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(CcsiSecurityService.class);

    public boolean isOwner(Principal principal, Long tenantId) {
        LOG.debug("Checking if owner. principal={}, tenantId={}", principal, tenantId);
        return false;
    }
}
