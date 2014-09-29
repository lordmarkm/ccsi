package com.ccsi.commons.dto;

import org.springframework.core.style.ToStringCreator;

public class ReplyMessageInfo extends OutgoingMessageInfo {

    /**
     * Amount you desire to charge the user who will receive the message. This will be deducted from the user's actual load.
     * Possible values:
     * For SMART & Globe: FREE, P1.00, P2.50, P5.00, P10.00, P15.00
     * For SUN: P2.00
     */
    private String request_cost;
    private String request_id;

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
            .append("req cost", request_cost)
            .append("req id", request_id)
            .toString();
    }

    public String getRequest_cost() {
        return request_cost;
    }

    public void setRequest_cost(String request_cost) {
        this.request_cost = request_cost;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
