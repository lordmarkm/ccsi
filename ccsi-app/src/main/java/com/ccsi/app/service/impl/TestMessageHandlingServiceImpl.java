package com.ccsi.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsi.app.client.ChikkaClient;
import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.reference.ReservedWord;
import com.ccsi.app.service.StockTemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.service.TestMessageHandlingService;
import com.ccsi.app.util.MessageComposer;
import com.ccsi.app.util.MessageUtil;

/**
 * @author markm
 */
@Service
public class TestMessageHandlingServiceImpl implements TestMessageHandlingService {

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
    private MessageComposer messageComposer;

    @Override
    public String handleIncomingMessage(String msg) {
        return sendReply(msg);
    }

    private String sendReply(String msg) {
        String[] breakdown = null;
        try {
            breakdown = MessageUtil.messageBreakdown(msg);
        } catch (Exception e) {
            return "Your message was invalid. Please follow this format: <keyword> <trackingNo>.";
        }

        String tenantCode = breakdown[0];
        String trackingNoOrKeyword = breakdown[1];
        LOG.debug("Determination complete. trackingNo/keyword={}, tenantCode={}", trackingNoOrKeyword, tenantCode);

        Tenant tenant = tenantService.findByKeywordIgnoreCase(tenantCode);
        if (null == tenant) {
            return "We could not find a business with keyword " + tenantCode + ".";
        }

        //check for update requests first
        if (trackingNoOrKeyword.toUpperCase().startsWith(ReservedWord.UPDATE.getKeyword())) {
            return "Update operations not supported by test client.";
        }

        //check for stock templates second
        StockTemplate stock = stockTemplateService.findByTenantAndKeyword(tenant, trackingNoOrKeyword);
        if (null != stock) {
            return stock.getReply();
        }

        //if a stock template is not found, then check for tenant records
        TenantRecord record = tenantRecordService.findByTrackingNoIgnoreCaseAndTenant_id(trackingNoOrKeyword, tenant.getId());
        if (null == record) {
            return "We could not find a keyword or transaction record matching " + trackingNoOrKeyword + ".";
        }

        return  messageComposer.composeMessage(record);
    }
}
