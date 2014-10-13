package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.TemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.util.MessageComposer;
import com.ccsi.commons.dto.tenant.TemplateInfo;
import com.ccsi.commons.dto.tenant.TemplatePreview;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("/preview/{tenantId}/{tenantRecordId}")
@PreAuthorize("@ccsiSecurityService.isOwner(#principal, #tenantId)")
public class PreviewResource {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TenantRecordService tenantRecordService;

    @Autowired
    private MessageComposer messageComposer;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TemplatePreview>> preview(Principal principal, @PathVariable Long tenantId, @PathVariable Long tenantRecordId) {
        Sort sort = new Sort(Direction.ASC, "status");
        List<TemplateInfo> templates = templateService.findInfoByTenantId(tenantId, sort);
        TenantRecord record = tenantRecordService.findOne(tenantRecordId);

        List<TemplatePreview> previews = Lists.newArrayList();
        for (TemplateInfo template : templates) {
            TemplatePreview preview = new TemplatePreview();
            preview.setTemplate(template);
            preview.setPreview(messageComposer.composeMessage(record, template));
            if (ObjectUtils.equals(template.getStatus(), record.getStatus().getStatus())) {
                preview.setActive(true);
            }
            previews.add(preview);
        }

        return new ResponseEntity<>(previews, OK);
    }
}
