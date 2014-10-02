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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.app.service.StockTemplateService;
import com.ccsi.commons.dto.tenant.StockTemplateInfo;

@RestController
@RequestMapping("/stock/{tenantId}")
public class StockTemplateResource extends GenericController {
    private static Logger LOG = LoggerFactory.getLogger(StockTemplateResource.class);

    @Autowired
    private StockTemplateService service;

    @RequestMapping(method = GET)
    public ResponseEntity<List<StockTemplateInfo>> findByTenant(@PathVariable Long tenantId) {
        LOG.debug("Finding templates by tenant id. id={}", tenantId);
        Sort sort = new Sort(Direction.ASC, "keyword");
        return new ResponseEntity<>(service.findInfoByTenantId(tenantId, sort), OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Object> save(@PathVariable Long tenantId,
            @Valid @RequestBody StockTemplateInfo template,
            BindingResult binding) {
        LOG.debug("Template save request. tenant={}, template={}", tenantId, template);
        if (binding.hasErrors()) {
            return new ResponseEntity<Object>(firstError(binding), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(service.saveInfo(tenantId, template), OK);
    }

    //TODO need @PreAuthorized here for sure
    @RequestMapping(value = "/{stockId}", method = DELETE)
    public ResponseEntity<String> delete(Principal principal, @PathVariable Long tenantId, @PathVariable Long stockId) {
        LOG.debug("Delete stock template request. tenant={}, stockId={}", tenantId, stockId);
        service.delete(stockId);
        return new ResponseEntity<>("Ok", OK);
    }

}
