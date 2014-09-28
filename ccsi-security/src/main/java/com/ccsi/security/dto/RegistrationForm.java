package com.ccsi.security.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.style.ToStringCreator;

public class RegistrationForm {

    @NotBlank(message = "Username can't be null")
    private String username;

    @NotBlank(message = "Password can't be null")
    private String password;

    @NotBlank(message = "Confirm password can't be null")
    private String confirmPassword;

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("username", username)
            .append("password", null != password)
            .append("confirmPassword", null != confirmPassword)
            .toString();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
