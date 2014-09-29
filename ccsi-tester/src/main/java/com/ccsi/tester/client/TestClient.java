package com.ccsi.tester.client;

import java.util.Date;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.ccsi.commons.dto.IncomingMessageInfo;

public class TestClient {

    private RestTemplate rest = new RestTemplate();

    private static final String url = "http://localhost:8080/message";

    @Test
    public void sendTest() {
        IncomingMessageInfo msg = new IncomingMessageInfo();
        msg.setMessage_type("incoming");
        msg.setMobile_number("639155411987");
        msg.setShortcode("292902274");
        msg.setRequest_id("12345");
        msg.setMessage("gg oeOInx");
        msg.setTimestamp(new Date().getTime());

        System.out.println("Sending message");
        String response = rest.postForObject(url, msg, String.class);
        System.err.println(response);
    }

}
