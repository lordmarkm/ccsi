package com.ccsi.tester.client;

import java.util.Date;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ccsi.commons.dto.IncomingMessageInfo;
import com.ccsi.commons.dto.ReplyMessageInfo;

public class TestClient {

    private RestTemplate rest = new RestTemplate();

    private static final String url = "http://localhost:8080/message";
    //private static final String url = "http://localhost:20058/ccsi/message";

    @Test
    public void sendTest() {
        IncomingMessageInfo msg = new IncomingMessageInfo();
        msg.setMessage_type("incoming");
        msg.setMobile_number("639155411987");
        msg.setShortcode("292902274");
        msg.setRequest_id("12345");
        msg.setMessage("laundry update 45UP0  ggs");
        msg.setTimestamp(String.valueOf(new Date().getTime()));

        LinkedMultiValueMap<String, Object> m = new LinkedMultiValueMap<String, Object>();
        m.add("message_type", msg.getMessage_type());
        m.add("mobile_number", msg.getMobile_number());
        m.add("shortcode", msg.getShortcode());
        m.add("request_id", msg.getRequest_id());
        m.add("message", msg.getMessage());
        m.add("timestamp", msg.getTimestamp());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(m, headers);

        System.out.println("Sending message");
        String response = rest.postForObject(url, request, String.class);
        System.err.println(response);
    }

}
