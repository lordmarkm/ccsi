package com.ccsi.tester.client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.ccsi.commons.dto.ReplyMessageInfo;
import com.ccsi.tester.errorhandler.MyResponseErrorHandler;

public class TestReplyClient {

    private RestTemplate rest = new RestTemplate();
    private static final String url = "https://post.chikka.com/smsapi/request";

    /*
    23:22:59.559 [taskExecutor-1] DEBUG o.s.web.client.RestTemplate - 
    Writing [[ReplyMessageInfo@3950d2fe msg type = 'REPLY', mobile# = '639155411987',
    shortcode = '292902274', msg id = 'lvZZHubYhzVHscfPWbFlnrLiRwPiksjG',
    msg = 'Dear Mr./Ms. Mark Martinez, your laundry is ready for pickup today',
    client_id = '78ad5bcb3f5422208a2c48d8ff3c14973cf7f3b7111d08f7f4bbe708620941db',
    secret_key = 'f74a6286b048eed7574878cf8eade3a047691dd78ccd43783fefca7f249f65e9',
    req cost = 'FREE',
    req id = '5048303030474C4F4245303030303239323930303038303030303030303038353030303036333931353534313139383730303030333030393134313434303532']]
    using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@47f606ae]
    */

    @Before
    public void init() {
        rest.setErrorHandler(new MyResponseErrorHandler());
    }
    @Test
    public void sendReply() {
        ReplyMessageInfo reply = new ReplyMessageInfo();
        reply.setMessage_type("REPLY");
        reply.setMobile_number("639155411987");
        reply.setShortcode("292902274");
        reply.setMessage_id("lvZZHubYhzVHscfPWbFlnrLiRwPiksjG");
        reply.setMessage("Hello world");
        reply.setClient_id("78ad5bcb3f5422208a2c48d8ff3c14973cf7f3b7111d08f7f4bbe708620941db");
        reply.setSecret_key("f74a6286b048eed7574878cf8eade3a047691dd78ccd43783fefca7f249f65e9");
        reply.setRequest_cost("FREE");
        reply.setRequest_id("5048303030474C4F4245303030303239323930303038303030303030303038353030303036333931353534313139383730303030333030393134313434303532");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<ReplyMessageInfo> request = new HttpEntity<>(reply, headers);

        System.out.println("Sending message");
        String response = rest.postForObject(url, request, String.class);
        System.out.println(response);
    }
}
