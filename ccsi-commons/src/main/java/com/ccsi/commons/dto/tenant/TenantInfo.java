package com.ccsi.commons.dto.tenant;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TenantInfo extends BaseCcsiInfo {

    @NotBlank(message = "Tenant name can't be blank!")
    protected String name;

    @NotBlank(message = "Keyword can't be blank!")
    @Length(min = 5, max = 20, message = "Keywords must be between 5 and 20 characters in length")
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "Please choose an alphanumeric keyword with no spaces.")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
