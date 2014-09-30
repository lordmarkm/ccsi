package com.ccsi.commons.dto;

import org.springframework.core.style.ToStringCreator;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author mbmartinez
 */
public class OutgoingMessageInfo extends LinkedMultiValueMap<String, Object> {

    private static final long serialVersionUID = 1L;

    protected String message_type;
    protected String mobile_number;
    protected String shortcode;
    protected String message_id;
    protected String message;
    protected String client_id;
    protected String secret_key;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("msg type", message_type)
            .append("mobile#", mobile_number)
            .append("shortcode", shortcode)
            .append("msg id", message_id)
            .append("msg", message)
            .append("client_id", client_id)
            .append("secret_key", secret_key)
            .toString();
    }

    public String getMessage_type() {
        return message_type;
    }
    public void setMessage_type(String message_type) {
        add("message_type", message_type);
        this.message_type = message_type;
    }
    public String getMobile_number() {
        return mobile_number;
    }
    public void setMobile_number(String mobile_number) {
        add("mobile_number", mobile_number);
        this.mobile_number = mobile_number;
    }
    public String getShortcode() {
        return shortcode;
    }
    public void setShortcode(String shortcode) {
        add("shortcode", shortcode);
        this.shortcode = shortcode;
    }
    public String getMessage_id() {
        return message_id;
    }
    public void setMessage_id(String message_id) {
        add("message_id", message_id);
        this.message_id = message_id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        add("message", message);
        this.message = message;
    }
    public String getClient_id() {
        return client_id;
    }
    public void setClient_id(String client_id) {
        add("client_id", client_id);
        this.client_id = client_id;
    }
    public String getSecret_key() {
        return secret_key;
    }
    public void setSecret_key(String secret_key) {
        add("secret_key", secret_key);
        this.secret_key = secret_key;
    }

}
