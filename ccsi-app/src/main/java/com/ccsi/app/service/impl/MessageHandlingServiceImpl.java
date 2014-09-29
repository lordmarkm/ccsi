package com.ccsi.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsi.app.client.ChikkaClient;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.service.MessageHandlingService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.commons.dto.DeliveryNotificationInfo;
import com.ccsi.commons.dto.IncomingMessageInfo;
import com.google.common.base.Splitter;

@Service
public class MessageHandlingServiceImpl implements MessageHandlingService {

    private static Logger LOG = LoggerFactory.getLogger(MessageHandlingServiceImpl.class);

//    private static final Splitter tiago = Splitter.on(' ')
//            .trimResults()
//            .omitEmptyStrings();

    @Autowired
    private ChikkaClient client;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantRecordService tenantRecordService;

    @Override
    public void handleIncomingMessage(IncomingMessageInfo msg) {
        String[] breakdown = null;
        try {
            breakdown = messageBreakdown(msg.getMessage());
        } catch (Exception e) {
            client.sendInvalidMessageMessage();
            return;
        }

        String tenantCode = breakdown[0];
        String trackingNo = breakdown[1];

        Tenant tenant = tenantService.findByKeyword(tenantCode);
        if (null == tenant) {
            client.sendInvalidTenantMessage(msg);
            return;
        }

        TenantRecord record = tenantRecordService.findByTrackingNoAndTenant_id(trackingNo, tenant.getId());
        if (null == record) {
            client.sendInvalidTrackingNo(msg);
            return;
        }

        client.sendTemplateReply(record, msg);
    }

    @Override
    public void handleDeliveryNotification(DeliveryNotificationInfo notif) {
        // TODO What do we do with these?
        LOG.debug("Delivery notification received. notif={}", notif);
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

    public static void main(String[] args) throws Exception {
        String test = "HELLO WORLD 1JiKl";

        MessageHandlingServiceImpl mhsi = new MessageHandlingServiceImpl();
        String[] result = mhsi.messageBreakdown(test);
        for (String s : result) {
            System.out.println("[" + s + "]");
        }
    }
}
