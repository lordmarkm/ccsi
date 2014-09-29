package com.ccsi.app.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ccsi.app.entity.TenantRecord;
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

    public void sendInvalidTenantMessage(IncomingMessageInfo msg) {
        // TODO Auto-generated method stub
        
    }

    public void sendInvalidTrackingNo(IncomingMessageInfo msg) {
        // TODO Auto-generated method stub
        
    }

    public void sendInvalidMessageMessage() {
        // TODO Auto-generated method stub
        
    }

    public void sendTemplateReply(TenantRecord record, IncomingMessageInfo msg) {
        ReplyMessageInfo out = new ReplyMessageInfo();
        out.setMessage_type("REPLY");
        out.setMobile_number(msg.getMobile_number());
        out.setShortcode(env.getProperty("shortcode"));
        out.setRequest_id(msg.getRequest_id());
        out.setMessage_id(messageUtil.generateId());
        out.setMessage(messageComposer.composeMessage(record));
        out.setRequest_cost(messageUtil.determineCost(msg));
        out.setClient_id(env.getProperty("client_id"));
        out.setSecret_key(env.getProperty("secret_key"));

        String endpoint = env.getProperty("chikka_endpoint");
        LOG.debug("About to send reply message. endpt={}, msg={}", endpoint, out);
        GenericHttpResponse response = rest.postForObject(endpoint, out, GenericHttpResponse.class);
        LOG.debug("Received response from chikka. response={}", response);
    }

}
