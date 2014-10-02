package com.ccsi.commons.dto.tenant;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.style.ToStringCreator;

/**
 * @author mbmartinez
 */
public class TemplateInfo extends BaseCcsiInfo {

    @NotBlank(message = "Status can't be empty!")
    private String status;
    @NotBlank(message = "Template string can't be empty!")
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
