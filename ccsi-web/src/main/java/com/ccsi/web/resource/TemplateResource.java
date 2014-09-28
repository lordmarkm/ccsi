package com.ccsi.web.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.service.TemplateService;
import com.ccsi.commons.dto.tenant.TemplateInfo;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author mbmartinez
 */
@RestController
@RequestMapping("/template/{tenantId}")
public class TemplateResource {

    private static Logger LOG = LoggerFactory.getLogger(TemplateResource.class);

    @Autowired
    private TemplateService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TemplateInfo>> findByTenant(@PathVariable Long tenantId) {
        LOG.debug("Finding templates by tenant id. id={}", tenantId);
        return new ResponseEntity<>(service.findInfoByTenantId(tenantId), OK);
    }

}
