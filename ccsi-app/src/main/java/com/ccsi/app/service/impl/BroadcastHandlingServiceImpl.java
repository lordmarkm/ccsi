package com.ccsi.app.service.impl;

import static com.ccsi.app.util.BigDecimalUtil.zeroIfNull;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ccsi.app.client.ChikkaClient;
import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.exception.InsufficientPushCreditsException;
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
    private Environment env;

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

    private BigDecimal costPerPush;

    @PostConstruct
    public void init() {
        costPerPush = env.getProperty("credits_per_push", BigDecimal.class);
    }

    private void push(Tenant tenant, TenantRecord record, String message) {
        TransactionRecord txn = createTransactionRecord(record);
        txn.setOutgoingMessage(message);
        txn.setTenant(tenant);
        client.push(txn);
    }

    private TransactionRecord createTransactionRecord(TenantRecord record) {
        TransactionRecord txn = new TransactionRecord();
        txn.setRecord(record);
        txn.setMobileNumber(record.getBroadcastNo());
        txn.setTransactionDate(LocalDateTime.now());

        //Push messages can't be charged to subscriber
        txn.setCost(ZERO);
        return txn;
    }

    private BigDecimal chargePushCredits(Tenant tenant, int count) throws InsufficientPushCreditsException {
        BigDecimal pushcost = costPerPush.multiply(new BigDecimal(count));
        if (zeroIfNull(tenant.getPushCredits()).compareTo(pushcost) < 0) {
            throw new InsufficientPushCreditsException("Insufficient vespene gas.");
        }
        tenant.setPushCredits(tenant.getPushCredits().subtract(pushcost));
        return tenantService.save(tenant).getPushCredits();
    }

    @Override
    public BigDecimal broadcastStatus(Long tenantId, Map<String, String> optionalParams) throws InsufficientPushCreditsException {
        Tenant tenant = tenantService.findOne(tenantId);
        Preconditions.checkNotNull(tenant, "Tenant not found with id=" + tenantId);

        LOG.debug("Doing status-based broadcast. tenant={}, params={}", tenantId, optionalParams);
        List<TenantRecord> records = recordService.findAllByParams(tenantId, optionalParams);
        LOG.debug("Found records for broadcast. count={}", records.size());
        BigDecimal newbalance = chargePushCredits(tenant, records.size());
        for (TenantRecord record : records) {
            if (null == record.getStatus()) {
                continue;
            }
            push(tenant, record, messageComposer.composeMessage(record));
        }
        return newbalance;
    }

    @Override
    public BigDecimal broadcastStock(Long tenantId, Map<String, String> optionalParams) throws InsufficientPushCreditsException {
        LOG.debug("Doing stock-template broadcast. tenant={}, params={}", tenantId, optionalParams);

        String keyword = optionalParams.get("keyword");
        Preconditions.checkNotNull(keyword, "Keyword required");
        Tenant tenant = tenantService.findOne(tenantId);
        Preconditions.checkNotNull(tenant, "Tenant not found with id=" + tenantId);
        StockTemplate stockTemplate = stockTemplateService.findByTenantAndKeyword(tenant, keyword);
        Preconditions.checkNotNull(stockTemplate, "Stock template not found with keyword=" + keyword);

        List<TenantRecord> records = recordService.findAllByParams(tenantId, optionalParams);
        LOG.debug("Found records for broadcast. count={}", records.size());
        BigDecimal newbalance = chargePushCredits(tenant, records.size());
        for (TenantRecord record : records) {
            push(tenant, record, messageComposer.composeMessage(record, stockTemplate.getReply()));
        }
        return newbalance;
    }

    @Override
    public BigDecimal broadcastCustom(Long tenantId, Map<String, String> optionalParams) throws InsufficientPushCreditsException {
        LOG.debug("Doing custom msg broadcast. tenant={}, params={}", tenantId, optionalParams);

        String customBroadcast = optionalParams.get("customBroadcast");
        Preconditions.checkNotNull(customBroadcast, "Custom message required");
        Tenant tenant = tenantService.findOne(tenantId);
        Preconditions.checkNotNull(tenant, "Tenant not found with id=" + tenantId);

        List<TenantRecord> records = recordService.findAllByParams(tenantId, optionalParams);
        LOG.debug("Found records for broadcast. count={}", records.size());
        BigDecimal newbalance = chargePushCredits(tenant, records.size());
        for (TenantRecord record : records) {
            push(tenant, record, messageComposer.composeMessage(record, customBroadcast));
        }
        return newbalance;
    }

}
