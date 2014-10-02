package com.ccsi.commons.dto.tenant;

/**
 * @author mbmartinez
 */
public class TemplatePreview {

    private TemplateInfo template;
    private String preview;
    private boolean active;

    public TemplateInfo getTemplate() {
        return template;
    }
    public void setTemplate(TemplateInfo template) {
        this.template = template;
    }
    public String getPreview() {
        return preview;
    }
    public void setPreview(String preview) {
        this.preview = preview;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

}
