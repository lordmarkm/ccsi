package com.ccsi.app.service.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsi.app.entity.Template;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.TemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

@Service
public class StatusUpdater {

    @Autowired
    private TenantRecordService recordService;

    @Autowired
    private TemplateService templateService;

    /**
     * Update requests must always be: Update &lt;trackingNo&gt; &lt;status&gt;
     * where status is a template keyword.
     * @param trackingNoOrKeyword
     * @param txn
     */
    public void doUpdate(Tenant tenant, String updateRequest, TransactionRecord txn) throws IllegalArgumentException {
        String[] breakdown = null;
        try {
            breakdown = updateBreakdown(updateRequest);
        } catch (Exception e) {
            txn.setOutgoingMessage("Bad update request. Format must always be 'Update <trackingNo> <status>'");
            return;
        }

        String trackingNo = breakdown[0];
        String status = breakdown[1];

        TenantRecord record = recordService.findByTrackingNoIgnoreCaseAndTenant_id(trackingNo, tenant.getId());
        if (null == record) {
            txn.setOutgoingMessage("Record not found with tracking no " + trackingNo);
            return;
        }

        Template template = templateService.findByTenant_idAndStatus(tenant.getId(), status);
        if (null == template) {
            txn.setOutgoingMessage("Template not found with status " + status);
            return;
        }

        record.setStatus(template);
        record = recordService.save(record);

        txn.setRecord(record);
        txn.setOutgoingMessage("Successful update. trackingNo=" + trackingNo + ", status=" + status);
    }

    public String[] updateBreakdownMultiwordStatus(String updateRequest) {
        Iterable<String> splitted = Splitter.on(' ').trimResults()
            .omitEmptyStrings()
            .split(updateRequest);

        String trackingNo = null;
        StringBuilder status = new StringBuilder();

        Iterator<String> it = splitted.iterator();
        it.next(); //discard 'update'
        trackingNo = it.next();
        while (it.hasNext()) {
            status.append(it.next());
            if (it.hasNext()) status.append(' ');
        }

        return new String[] {trackingNo, status.toString()};
    }

    @Deprecated
    public String[] updateBreakdown(String updateRequest) {
        String sanitized = updateRequest.replaceAll("\\s+", " ").trim();
        String[] updateBreakdown = sanitized.split(" ");
        Preconditions.checkArgument(updateBreakdown.length == 3, "Bad update request. Format must always be 'Update <trackingNo> <status>'");
        return new String[] {updateBreakdown[1], updateBreakdown[2]};
    }
}