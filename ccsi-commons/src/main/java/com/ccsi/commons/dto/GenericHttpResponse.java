package com.ccsi.commons.dto;

import org.springframework.core.style.ToStringCreator;

/**
 * @author mbmartinez
 */
public class GenericHttpResponse {

    private String status;
    private String message;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("status", status)
            .append("message", message)
            .toString();
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
