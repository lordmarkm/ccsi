package com.ccsi.web.endpoint;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsi.app.service.TestMessageHandlingService;
import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.ReplyMessageInfo;

@RestController
public class TestResource {

    private static Logger LOG = LoggerFactory.getLogger(TestResource.class);

    @Autowired
    private TestMessageHandlingService handler;

    //Test message
    @RequestMapping(value = "/testmsg", method = GET)
    public ResponseEntity<String> testMessage(@RequestParam String msg) {
        LOG.debug("Test message received. msg={}", msg);
        return new ResponseEntity<>(handler.handleIncomingMessage(msg), HttpStatus.OK);
    }

    //Test reply
    @RequestMapping(value = "/testreply", method = POST)
    public ResponseEntity<GenericHttpResponse> testReply(@ModelAttribute ReplyMessageInfo reply) {
        LOG.debug("Reply received. msg={}", reply);
        GenericHttpResponse resp = new GenericHttpResponse();
        resp.setStatus("200");
        resp.setMessage("OK");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
