package com.ccsi.commons.dto;

/**
 * @author mbmartinez
 */
public class OutgoingMessageInfo {

    private String message_type;
    private String mobile_number;
    private String shortcode;
    private String message_id;
    private String message;
    private String client_id;
    private String secret_key;

    public String getMessage_type() {
        return message_type;
    }
    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }
    public String getMobile_number() {
        return mobile_number;
    }
    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
    public String getShortcode() {
        return shortcode;
    }
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }
    public String getMessage_id() {
        return message_id;
    }
    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getClient_id() {
        return client_id;
    }
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    public String getSecret_key() {
        return secret_key;
    }
    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

}
