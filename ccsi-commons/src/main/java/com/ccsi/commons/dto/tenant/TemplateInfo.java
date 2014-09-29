package com.ccsi.commons.dto.tenant;

import org.springframework.core.style.ToStringCreator;

/**
 * @author mbmartinez
 */
public class TemplateInfo extends BaseCcsiInfo {

    private String status;
    private String templateString;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("status", status)
            .append("templateString", templateString)
            .toString();
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTemplateString() {
        return templateString;
    }
    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

}
