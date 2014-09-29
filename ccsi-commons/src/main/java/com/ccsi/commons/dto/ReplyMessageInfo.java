package com.ccsi.commons.dto;

public class ReplyMessageInfo extends OutgoingMessageInfo {

    /**
     * Amount you desire to charge the user who will receive the message. This will be deducted from the user's actual load.
     * Possible values:
     * For SMART & Globe: FREE, P1.00, P2.50, P5.00, P10.00, P15.00
     * For SUN: P2.00
     */
    private String request_cost;
    private String request_id;

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
