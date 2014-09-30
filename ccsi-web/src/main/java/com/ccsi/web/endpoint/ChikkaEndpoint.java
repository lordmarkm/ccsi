package com.ccsi.web.endpoint;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccsi.app.service.MessageHandlingService;
import com.ccsi.commons.dto.DeliveryNotificationInfo;
import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.IncomingMessageInfo;
import com.ccsi.commons.dto.ReplyMessageInfo;

/**
 * @author mbmartinez
 */
@Controller
public class ChikkaEndpoint {

    private static Logger LOG = LoggerFactory.getLogger(ChikkaEndpoint.class);

    @Autowired
    private MessageHandlingService handler;

    @RequestMapping(value = "/message", method = POST)
    public ResponseEntity<String> message(@ModelAttribute IncomingMessageInfo msg) {
        LOG.debug("Message received. msg={}", msg);
        handler.handleIncomingMessage(msg);
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/notification", method = POST)
    public ResponseEntity<String> notification(@ModelAttribute DeliveryNotificationInfo msg) {
        LOG.debug("Delivery notification received. msg={}", msg);
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/test", method = POST)
    public ResponseEntity<GenericHttpResponse> test(@ModelAttribute ReplyMessageInfo reply) {
        LOG.debug("Reply received. msg={}", reply);
        GenericHttpResponse resp = new GenericHttpResponse();
        resp.setStatus("200");
        resp.setMessage("OK");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
