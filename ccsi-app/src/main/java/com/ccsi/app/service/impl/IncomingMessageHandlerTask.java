package com.ccsi.app.service.impl;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ccsi.app.client.ChikkaClient;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.commons.dto.IncomingMessageInfo;

@Component
@Scope("prototype")
public class IncomingMessageHandlerTask implements Runnable {

    private static Logger LOG = LoggerFactory.getLogger(IncomingMessageHandlerTask.class);

    @Autowired
    private ChikkaClient client;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantRecordService tenantRecordService;

    private IncomingMessageInfo msg;
    private TransactionRecord txn;

    @Override
    public void run() {
        createTransactionRecord();
        sendReply();
    }

    private void createTransactionRecord() {
        txn = new TransactionRecord();
        txn.setIncomingMessage(msg.getMessage());
        txn.setRequestId(msg.getRequest_id());
        txn.setMobileNumber(msg.getMobile_number());
        txn.setTransactionDate(new LocalDateTime());
    }

    private void sendReply() {
        String[] breakdown = null;
        try {
            breakdown = messageBreakdown(msg.getMessage());
        } catch (Exception e) {
            client.sendInvalidMessageMessage(txn);
            return;
        }

        String tenantCode = breakdown[0];
        String trackingNo = breakdown[1];

        Tenant tenant = tenantService.findByKeyword(tenantCode);
        if (null == tenant) {
            client.sendInvalidTenantMessage(msg, txn);
            return;
        }
        txn.setTenant(tenant);

        TenantRecord record = tenantRecordService.findByTrackingNoAndTenant_id(trackingNo, tenant.getId());
        if (null == record) {
            client.sendInvalidTrackingNo(msg, txn);
            return;
        }
        txn.setRecord(record);

        client.sendTemplateReply(record, msg, txn);
    }

    /**
     * Tenant keyword = everything that comes before the last element
     * Tracking no = always the last element
     */
    private String[] messageBreakdown(String message) throws Exception {
        if (null == message || message.length() < 1) {
            return null;
        }
        String msg = message.trim();
        int lastSpace = msg.lastIndexOf(' ');
        String trackingNo = msg.substring(lastSpace + 1);
        String tenantCode = msg.substring(0, lastSpace);

        LOG.debug("Determination complete. trackingNo={}, tenantCode={}", trackingNo, tenantCode);

        return new String[]{tenantCode, trackingNo};
    }

    public void setMsg(IncomingMessageInfo msg) {
        this.msg = msg;
    }
}
