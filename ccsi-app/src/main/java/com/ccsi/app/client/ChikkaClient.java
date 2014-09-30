package com.ccsi.app.client;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.app.util.MessageComposer;
import com.ccsi.app.util.MessageUtil;
import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.IncomingMessageInfo;
import com.ccsi.commons.dto.ReplyMessageInfo;

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
    private TransactionRecordService txnRecordService;

    public void sendInvalidTenantMessage(IncomingMessageInfo msg, TransactionRecord txn, String tenantCode) {
        String outgoingMessage = "We could not find a tenant with code " + tenantCode;
        sendMessage(outgoingMessage, msg, txn);
    }

    public void sendInvalidTrackingNo(IncomingMessageInfo msg, TransactionRecord txn, String trackingNo) {
        String outgoingMessage = "We could not find a transaction record with tracking number " + trackingNo;
        sendMessage(outgoingMessage, msg, txn);
    }

    public void sendInvalidMessageMessage(IncomingMessageInfo msg, TransactionRecord txn) {
        String outgoingMessage = "Your message was invalid. Please follow this format: <keyword> <trackingNo>.";
        sendMessage(outgoingMessage, msg, txn);
    }

    public void sendTemplateReply(TenantRecord record, IncomingMessageInfo msg, TransactionRecord txn) {
        String outgoingMessage = messageComposer.composeMessage(record);
        sendMessage(outgoingMessage, msg, txn);
    }

    private void sendMessage(String outgoingMessage, IncomingMessageInfo msg, TransactionRecord txn) {
        ReplyMessageInfo out = new ReplyMessageInfo();
        out.setMessage_type("REPLY");
        out.setMobile_number(msg.getMobile_number());
        out.setShortcode(env.getProperty("shortcode"));
        out.setRequest_id(msg.getRequest_id());
        out.setMessage_id(messageUtil.generateId());
        out.setMessage(outgoingMessage);
        out.setRequest_cost(messageUtil.determineCost(msg));
        out.setClient_id(env.getProperty("client_id"));
        out.setSecret_key(env.getProperty("secret_key"));

        txn.setCost(tryParse(out.getRequest_cost()));
        txn.setOutgoingMessage(out.getMessage());
        txn.setMessageId(out.getMessage_id());
        txnRecordService.save(txn);

        String endpoint = env.getProperty("chikka_endpoint");
        LOG.debug("About to send reply message. endpt={}, msg={}", endpoint, out);
        GenericHttpResponse response = rest.postForObject(endpoint, out, GenericHttpResponse.class);
        LOG.debug("Received response from chikka. response={}", response);
    }

    private BigDecimal tryParse(String request_cost) {
        try {
            return new BigDecimal(request_cost);
        } catch (NumberFormatException n) {
            return BigDecimal.ZERO;
        }
    }

}
