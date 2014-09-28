package com.ccsi.security.validator;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.ccsi.security.dto.RegistrationForm;

@Component
public class RegistrationFormValidator implements Validator {

    private static Logger LOG = LoggerFactory.getLogger(RegistrationFormValidator.class);

    @Autowired
    private LocalValidatorFactoryBean defaultValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOG.debug("Performing registration form validation.");

        defaultValidator.validate(target, errors);

        RegistrationForm reg = (RegistrationForm) target;
        if (!ObjectUtils.equals(reg.getPassword(), reg.getConfirmPassword())) {
            errors.rejectValue("password", "notmatch.password", "Passwords do not match");
        }

        LOG.debug("Validation complete. error count={}", errors.getErrorCount());
        for (ObjectError error : errors.getAllErrors()) {
            LOG.debug("Error! code={}, msg={}", error.getCode(), error.getDefaultMessage());
        }
    }

}
