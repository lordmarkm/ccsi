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
import com.ccsi.app.reference.ReservedWord;
import com.ccsi.app.service.StockTemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.app.util.MessageComposer;
import com.ccsi.app.util.MessageUtil;
import com.ccsi.app.util.NetworkAndPricingUtil;
import com.ccsi.commons.dto.IncomingMessageInfo;
import com.ccsi.commons.exception.NetworkNotSupportedException;
import com.google.common.base.Preconditions;

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

    @Autowired
    private StatusUpdater statusUpdater;

    @Autowired
    private NetworkAndPricingUtil networkAndPricingUtil;

    @Autowired
    private MessageComposer messageComposer;

    @Autowired
    private TransactionRecordService txnRecordService;

    private IncomingMessageInfo msg;
    private TransactionRecord txn;

    @Override
    public void run() {
        Preconditions.checkNotNull(msg);
        createTransactionRecord();
        try {
            prepareReply();
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No reply message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
            return;
        }
        doSend();
    }

    /**
     * Create transaction record but do not persist. Some txn records
     * may be from the test client and will not be persisted.
     */
    private void createTransactionRecord() {
        txn = new TransactionRecord();
        txn.setIncomingMessage(msg.getMessage());
        txn.setRequestId(msg.getRequest_id());
        txn.setMobileNumber(msg.getMobile_number());
        txn.setTransactionDate(new LocalDateTime());
        txn.setMobileNumber(msg.getMobile_number());
        txn.setRequestId(msg.getRequest_id());
    }

    private void prepareReply() throws NetworkNotSupportedException {
        String[] breakdown = null;
        try {
            breakdown = MessageUtil.messageBreakdown(msg.getMessage());
        } catch (Exception e) {
            txn.setOutgoingMessage("Your message was invalid. Please follow this format: <keyword> <trackingNo>.");
            return;
        }

        String tenantCode = breakdown[0];
        String trackingNoOrKeyword = breakdown[1];
        LOG.debug("Determination complete. trackingNo/keyword={}, tenantCode={}", trackingNoOrKeyword, tenantCode);

        Tenant tenant = tenantService.findByKeywordIgnoreCase(tenantCode);
        if (null == tenant) {
            txn.setOutgoingMessage("We could not find a business with keyword " + tenantCode + ".");
            networkAndPricingUtil.setErrorParams(txn);
            return;
        }
        txn.setTenant(tenant);

        //check for update requests first
        if (trackingNoOrKeyword.toUpperCase().startsWith(ReservedWord.UPDATE.getKeyword())) {
            statusUpdater.doUpdate(tenant, trackingNoOrKeyword, txn);
            networkAndPricingUtil.setReplyParams(tenant.getReplyCharge(), txn);
            return;
        }

        //check for stock templates second
        StockTemplate stock = stockTemplateService.findByTenantAndKeyword(tenant, trackingNoOrKeyword);
        if (null != stock) {
            txn.setOutgoingMessage(stock.getReply());
            networkAndPricingUtil.setReplyParams(tenant.getReplyCharge(), txn);
            return;
        }

        //if a stock template is not found, then check for tenant records
        TenantRecord record = tenantRecordService.findByTrackingNoIgnoreCaseAndTenant_id(trackingNoOrKeyword, tenant.getId());
        if (null == record) {
            txn.setOutgoingMessage("We could not find a keyword or transaction record matching " + trackingNoOrKeyword + ".");
            networkAndPricingUtil.setErrorParams(txn);
            return;
        }

        //If you reach this point, it's a template reply
        networkAndPricingUtil.setReplyParams(tenant.getReplyCharge(), txn);
        txn.setRecord(record);
        txn.setOutgoingMessage(messageComposer.composeMessage(record));
    }

    private void doSend() {
        client.reply(txn);
    }

    public void setMsg(IncomingMessageInfo msg) {
        this.msg = msg;
    }
}
