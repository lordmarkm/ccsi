package com.ccsi.app.service.impl;

import static java.math.BigDecimal.ZERO;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsi.app.client.ChikkaClient;
import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.BroadcastHandlingService;
import com.ccsi.app.service.StockTemplateService;
import com.ccsi.app.service.TenantRecordService;
import com.ccsi.app.service.TenantService;
import com.ccsi.app.util.MessageComposer;
import com.google.common.base.Preconditions;

/**
 * @author pilabaldeh
 */
@Service
public class BroadcastHandlingServiceImpl implements BroadcastHandlingService {

    private static Logger LOG = LoggerFactory.getLogger(BroadcastHandlingServiceImpl.class);

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantRecordService recordService;

    @Autowired
    private StockTemplateService stockTemplateService;

    @Autowired
    private MessageComposer messageComposer;

    @Autowired
    private ChikkaClient client;

    private void push(Long tenantId, TenantRecord record, String message) {
        TransactionRecord txn = createTransactionRecord(tenantId, record);
        txn.setOutgoingMessage(message);
        client.push(txn);
    }

    private TransactionRecord createTransactionRecord(Long tenantId, TenantRecord record) {
        TransactionRecord txn = new TransactionRecord();
        txn.setTenant(tenantService.findOne(tenantId));
        txn.setRecord(record);
        txn.setMobileNumber(record.getBroadcastNo());
        txn.setTransactionDate(LocalDateTime.now());

        //Push messages can't be charged to subscriber
        txn.setCost(ZERO);
        return txn;
    }

    @Override
    public void broadcastStatus(Long tenantId, Map<String, String> optionalParams) {
        LOG.debug("Doing status-based broadcast. tenant={}, params={}", tenantId, optionalParams);
        List<TenantRecord> records = recordService.findAllByParams(tenantId, optionalParams);
        LOG.debug("Found records for broadcast. count={}", records.size());
        for (TenantRecord record : records) {
            if (null == record.getStatus()) {
                continue;
            }
            push(tenantId, record, messageComposer.composeMessage(record));
        }
    }

    @Override
    public void broadcastStock(Long tenantId, Map<String, String> optionalParams) {
        LOG.debug("Doing stock-template broadcast. tenant={}, params={}", tenantId, optionalParams);

        String keyword = optionalParams.get("keyword");
        Preconditions.checkNotNull(keyword, "Keyword required");
        Tenant tenant = tenantService.findOne(tenantId);
        Preconditions.checkNotNull(tenant, "Tenant not found with id=" + tenantId);
        StockTemplate stockTemplate = stockTemplateService.findByTenantAndKeyword(tenant, keyword);
        Preconditions.checkNotNull(stockTemplate, "Stock template not found with keyword=" + keyword);

        List<TenantRecord> records = recordService.findAllByParams(tenantId, optionalParams);
        LOG.debug("Found records for broadcast. count={}", records.size());
        for (TenantRecord record : records) {
            push(tenantId, record, messageComposer.composeMessage(record, stockTemplate.getReply()));
        }
    }

    @Override
    public void broadcastCustom(Long tenantId, Map<String, String> optionalParams) {
        LOG.debug("Doing custom msg broadcast. tenant={}, params={}", tenantId, optionalParams);

        String customBroadcast = optionalParams.get("customBroadcast");
        Preconditions.checkNotNull(customBroadcast, "Custom message required");

        List<TenantRecord> records = recordService.findAllByParams(tenantId, optionalParams);
        LOG.debug("Found records for broadcast. count={}", records.size());
        for (TenantRecord record : records) {
            push(tenantId, record, messageComposer.composeMessage(record, customBroadcast));
        }
    }

}
