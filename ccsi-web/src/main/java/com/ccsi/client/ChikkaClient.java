package com.ccsi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ccsi.commons.dto.GenericHttpResponse;
import com.ccsi.commons.dto.OutgoingMessageInfo;

@Service
public class ChikkaClient {

    private static final String url = "http://localhost:8080/";

    @Autowired
    private RestTemplate rest;

    public void sendTest() {
        OutgoingMessageInfo msg = new OutgoingMessageInfo();
        GenericHttpResponse response = rest.postForObject(url, msg, GenericHttpResponse.class);
    }
}
