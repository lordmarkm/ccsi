package com.ccsi.commons.dto.tenant;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.style.ToStringCreator;


/**
 * @author mbmartinez
 */
public class StockTemplateInfo extends BaseCcsiInfo {

    @NotBlank(message = "Keyword can't be blank!")
    @Length(min = 6, message = "Keywords must be at least 6 characters long. (This prevents collision with tracking numbers).")
    private String keyword;
    @NotBlank(message = "Reply can't be blank!")
    private String reply;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("keyword", keyword)
            .append("reply", reply)
            .toString();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
