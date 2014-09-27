package com.ccsi.commons.dto;

import org.springframework.core.style.ToStringCreator;

/**
 * @author mbmartinez
 */
public class IncomingMessageInfo {

    private String message_type;
    private String mobile_number;
    private String shortcode;
    private String request_id;
    private String message;
    private long timestamp;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("msg type", message_type)
            .append("mobile #", mobile_number)
            .append("shortcode", shortcode)
            .append("req id", request_id)
            .append("msg", message)
            .append("timestamp", timestamp)
            .toString();
    }

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
    public String getRequest_id() {
        return request_id;
    }
    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
