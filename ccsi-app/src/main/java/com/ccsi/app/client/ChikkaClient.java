package com.ccsi.app.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.app.util.MessageUtil;
import com.ccsi.app.util.NetworkAndPricingUtil;
import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.OutgoingMessageInfo;
import com.ccsi.commons.dto.ReplyMessageInfo;
import com.google.common.base.Preconditions;

/**
 * New and improved Chikka client. Everything not constant must come from the
 * TransactionRecord (txn) now.
 * @author mbmartinez
 *
 */
@Service
@PropertySource("classpath:app.properties")
public class ChikkaClient {

    private static final Logger LOG = LoggerFactory.getLogger(ChikkaClient.class);

    @Autowired
    private Environment env;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private NetworkAndPricingUtil pricingUtil;

    @Autowired
    private TransactionRecordService txnRecordService;

    @Autowired
    private RestTemplate rest;

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

    public void reply(TransactionRecord txn) {
        validateReply(txn);
        replyToChikka(txn);
    }

    public void replyToTest(TransactionRecord txn) {
        
    }

    public void replyToChikka(TransactionRecord txn) {
        txn.setMessageId(messageUtil.generateId());
        txnRecordService.save(txn);

        ReplyMessageInfo out = new ReplyMessageInfo();
        out.setMessage_type("REPLY");
        out.setMobile_number(txn.getMobileNumber());
        out.setShortcode(shortcode);
        out.setRequest_id(txn.getRequestId());
        out.setMessage_id(txn.getMessageId());
        out.setMessage(txn.getOutgoingMessage());
        out.setRequest_cost(pricingUtil.asCostString(txn.getCost()));
        out.setClient_id(clientId);
        out.setSecret_key(secretKey);

        LOG.debug("About to send REPLY message. endpt={}, msg={}", endpoint, out);
        GenericHttpResponse response = rest.postForObject(endpoint, out, GenericHttpResponse.class);
        LOG.debug("Received response from chikka. response={}", response);
    }

    private void validateReply(TransactionRecord txn) {
        Preconditions.checkNotNull(txn.getMobileNumber(), "Mobile can't be null!");
        Preconditions.checkNotNull(txn.getRequestId(), "Request id can't be null!");
        Preconditions.checkNotNull(txn.getOutgoingMessage(), "Outgoing message can't be null!");
        Preconditions.checkNotNull(txn.getCost(), "Cost can't be null!");
    }
}
