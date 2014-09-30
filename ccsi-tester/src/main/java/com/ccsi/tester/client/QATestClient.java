package com.ccsi.tester.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ccsi.commons.dto.IncomingMessageInfo;
import com.ccsi.tester.errorhandler.MyResponseErrorHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class QATestClient {
    private RestTemplate rest = new RestTemplate();

    private static final String url = "http://tmat02.tomcathostingservice.com/ccsi/message";

    @Before
    public void init() {
        rest.setErrorHandler(new MyResponseErrorHandler());
    }

    @Test
    public void sendTest() {
        IncomingMessageInfo msg = new IncomingMessageInfo();
        msg.setMessage_type("incoming");
        msg.setMobile_number("639155411987");
        msg.setShortcode("292902274");
        msg.setRequest_id("12345");
        msg.setMessage("gg oeOInx");
        msg.setTimestamp(String.valueOf(new Date().getTime()));

        LinkedMultiValueMap<String, Object> m = new LinkedMultiValueMap<String, Object>();
        m.add("message_type", "incoming");
        m.add("mobile_number", "639155411987");
        m.add("shortcode", "292902274");
        m.add("request_id", "12345");
        m.add("message", "gg oeOInx");
        m.add("timestamp", String.valueOf(new Date().getTime()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(m, headers);

        System.out.println("Sending message");
        String response = rest.postForObject(url, request, String.class);
        System.err.println(response);
    }

}
