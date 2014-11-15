package com.ccsi.app.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ccsi.app.entity.StockTemplate;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.app.util.MessageComposer;
import com.ccsi.app.util.MessageUtil;
import com.ccsi.app.util.PricingUtil;
import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.IncomingMessageInfo;
import com.ccsi.commons.dto.OutgoingMessageInfo;
import com.ccsi.commons.dto.ReplyMessageInfo;
import com.ccsi.commons.exception.NetworkNotSupportedException;

@Service
@PropertySource("classpath:app.properties")
public class ChikkaClient {

    private static final Logger LOG = LoggerFactory.getLogger(ChikkaClient.class);

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate rest;

    @Autowired
    private MessageComposer messageComposer;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private PricingUtil pricingUtil;

    @Autowired
    private TransactionRecordService txnRecordService;

    private String endpoint;
    private String clientId;
    private String secretKey;
    private String shortcode;

    @PostConstruct
    public void init() {
        endpoint = env.getProperty("chikka_endpoint");
        clientId = env.getProperty("client_id");
        secretKey = env.getProperty("secret_key");
        shortcode = env.getProperty("shortcode");
    }

    public void sendInvalidTenantMessage(IncomingMessageInfo msg, TransactionRecord txn, String tenantCode) {
        String outgoingMessage = "We could not find a business with keyword " + tenantCode + ".";
        try {
            txn.setCost(pricingUtil.determineErrorCost(msg.getMobile_number(), txn));
            sendReply(outgoingMessage, msg, txn);
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No error message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
        }
    }

    public void sendInvalidTrackingNo(IncomingMessageInfo msg, TransactionRecord txn, String trackingNoOrKeyword) {
        String outgoingMessage = "We could not find a keyword or transaction record matching " + trackingNoOrKeyword + ".";
        try {
            txn.setCost(pricingUtil.determineErrorCost(msg.getMobile_number(), txn));
            sendReply(outgoingMessage, msg, txn);
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No error message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
        }
    }

    public void sendInvalidMessageMessage(IncomingMessageInfo msg, TransactionRecord txn) {
        String outgoingMessage = "Your message was invalid. Please follow this format: <keyword> <trackingNo>.";
        try {
            txn.setCost(pricingUtil.determineErrorCost(msg.getMobile_number(), txn));
            sendReply(outgoingMessage, msg, txn);
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No error message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
        }
    }

    public void sendStockTemplateReply(IncomingMessageInfo msg, TransactionRecord txn, StockTemplate stock) {
        try {
            txn.setCost(pricingUtil.determineReplyCost(txn.getTenant().getReplyCharge(), msg.getMobile_number(), txn));
            sendReply(stock.getReply(), msg, txn);
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No reply message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
        }
    }

    public void sendTemplateReply(TenantRecord record, IncomingMessageInfo msg, TransactionRecord txn) {
        String outgoingMessage = messageComposer.composeMessage(record);
        try {
            txn.setCost(pricingUtil.determineReplyCost(txn.getTenant().getReplyCharge(), msg.getMobile_number(), txn));
            sendReply(outgoingMessage, msg, txn);
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No reply message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
        }
    }

    public void sendUpdateReply(IncomingMessageInfo msg, TransactionRecord txn) {
        try {
            txn.setCost(pricingUtil.determineReplyCost(txn.getTenant().getReplyCharge(), msg.getMobile_number(), txn));
            sendReply(txn.getOutgoingMessage(), msg, txn);
        } catch (NetworkNotSupportedException e) {
            LOG.error("Network is not supported. No reply message to be sent. Mobile#={}", msg.getMobile_number());
            txnRecordService.save(txn);
        }
    }

    public void push(TransactionRecord txn) {
        OutgoingMessageInfo out = new OutgoingMessageInfo();
        out.setMessage_type("SEND");
        out.setMobile_number(txn.getMobileNumber());
        out.setShortcode(shortcode);
        out.setMessage_id(messageUtil.generateId());
        out.setMessage(txn.getOutgoingMessage());
        out.setClient_id(clientId);
        out.setSecret_key(secretKey);

        txn.setMessageId(out.getMessage_id());
        txnRecordService.save(txn);

        LOG.debug("About to send PUSH message. endpt={}, msg={}", endpoint, out);
        GenericHttpResponse response = rest.postForObject(endpoint, out, GenericHttpResponse.class);
        LOG.debug("Received response from chikka. response={}", response);
    }

    private void sendReply(String outgoingMessage, IncomingMessageInfo msg, TransactionRecord txn) {
        ReplyMessageInfo out = new ReplyMessageInfo();
        out.setMessage_type("REPLY");
        out.setMobile_number(msg.getMobile_number());
        out.setShortcode(shortcode);
        out.setRequest_id(msg.getRequest_id());
        out.setMessage_id(messageUtil.generateId());
        out.setMessage(outgoingMessage);
        out.setRequest_cost(pricingUtil.asCostString(txn.getCost()));
        out.setClient_id(clientId);
        out.setSecret_key(secretKey);

        txn.setOutgoingMessage(out.getMessage());
        txn.setMessageId(out.getMessage_id());
        txnRecordService.save(txn);

        LOG.debug("About to send REPLY message. endpt={}, msg={}", endpoint, out);
        GenericHttpResponse response = rest.postForObject(endpoint, out, GenericHttpResponse.class);
        LOG.debug("Received response from chikka. response={}", response);
    }

}
