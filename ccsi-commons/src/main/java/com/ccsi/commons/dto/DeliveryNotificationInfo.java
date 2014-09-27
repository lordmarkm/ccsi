package com.ccsi.commons.dto;

/**
 * @author mbmartinez
 */
public class DeliveryNotificationInfo {

    private String message_type;
    private String shortcode;
    private String message_id;
    private String status;
    private String credits_cost;
    private long timestamp;

    public String getMessage_type() {
        return message_type;
    }
    public void setMessage_type(String message_type) {
        this.message_type = message_type;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCredits_cost() {
        return credits_cost;
    }
    public void setCredits_cost(String credits_cost) {
        this.credits_cost = credits_cost;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
