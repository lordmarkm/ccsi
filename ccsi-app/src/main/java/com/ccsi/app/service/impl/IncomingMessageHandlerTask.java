package com.ccsi.app.service.impl;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ccsi.app.client.ChikkaClient;
import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.StockTemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.util.MessageUtil;
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

    @Autowired
    private StockTemplateService stockTemplateService;

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
            breakdown = MessageUtil.messageBreakdown(msg.getMessage());
        } catch (Exception e) {
            client.sendInvalidMessageMessage(msg, txn);
            return;
        }

        String tenantCode = breakdown[0];
        String trackingNoOrKeyword = breakdown[1];
        LOG.debug("Determination complete. trackingNo/keyword={}, tenantCode={}", trackingNoOrKeyword, tenantCode);

        Tenant tenant = tenantService.findByKeywordIgnoreCase(tenantCode);
        if (null == tenant) {
            client.sendInvalidTenantMessage(msg, txn, tenantCode);
            return;
        }
        txn.setTenant(tenant);

        //check for stock templates first
        StockTemplate stock = stockTemplateService.findByTenantAndKeyword(tenant, trackingNoOrKeyword);
        if (null != stock) {
            client.sendStockTemplateReply(msg, txn, stock);
            return;
        }

        //if a stock template is not found, then check for tenant records
        TenantRecord record = tenantRecordService.findByTrackingNoIgnoreCaseAndTenant_id(trackingNoOrKeyword, tenant.getId());
        if (null == record) {
            client.sendInvalidTrackingNo(msg, txn, trackingNoOrKeyword);
            return;
        }
        txn.setRecord(record);
        client.sendTemplateReply(record, msg, txn);
    }

    public void setMsg(IncomingMessageInfo msg) {
        this.msg = msg;
    }
}
