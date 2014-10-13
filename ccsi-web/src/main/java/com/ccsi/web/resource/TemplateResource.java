package com.ccsi.web.resource;

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

import com.ccsi.app.service.TemplateService;
import com.ccsi.commons.dto.tenant.TemplateInfo;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.http.HttpStatus.*;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/template/{tenantId}")
@PreAuthorize("@ccsiSecurityService.isOwner(#principal, #tenantId)")
public class TemplateResource {

    private static Logger LOG = LoggerFactory.getLogger(TemplateResource.class);

    @Autowired
    private TemplateService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TemplateInfo>> findByTenant(Principal principal, @PathVariable Long tenantId) {
        LOG.debug("Finding templates by tenant id. id={}", tenantId);
        return new ResponseEntity<>(service.findInfoByTenantId(tenantId, null), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<TemplateInfo> save(Principal principal, @PathVariable Long tenantId,
            @Valid @RequestBody TemplateInfo template) {
        LOG.debug("Saving template. tenant={}, template={}", template);
        return new ResponseEntity<>(service.saveInfo(tenantId, template), OK);
    }

    //TODO need @PreAuthorized here for sure
    @RequestMapping(value = "/{templateId}", method = DELETE)
    public ResponseEntity<String> delete(Principal principal, @PathVariable Long tenantId, @PathVariable Long templateId) {
        LOG.debug("Delete template request. tenant={}, template={}", tenantId, templateId);

        long recordCount = service.tryDelete(templateId);
        if (recordCount == 0) {
            return new ResponseEntity<>("Ok", OK);
        } else {
            String msg = "Can't delete this template due to " + recordCount + " dependent customer records. Change the status of those records then try again.";
            return new ResponseEntity<>(msg, INTERNAL_SERVER_ERROR);
        }
    }
}
