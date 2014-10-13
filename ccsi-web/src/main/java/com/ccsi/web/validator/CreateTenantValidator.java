package com.ccsi.web.validator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.ccsi.app.service.TenantService;
import com.ccsi.commons.dto.tenant.TenantInfo;

/**
 * @author mbmartinez
 */
@Component
public class CreateTenantValidator  implements Validator{

    private static Logger LOG = LoggerFactory.getLogger(CreateTenantValidator.class);

    @Autowired
    private LocalValidatorFactoryBean defaultValidator;

    @Autowired
    private TenantService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return TenantInfo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOG.debug("Performing registration form validation.");

        defaultValidator.validate(target, errors);

        TenantInfo tenant = (TenantInfo) target;
        if (tenant.getId() != null) {
            LOG.debug("Skip validating existing tenant.");
            return;
        }
        if (service.findByKeywordIgnoreCase(StringUtils.trimToEmpty(tenant.getKeyword())) != null) {
            errors.rejectValue("keyword", "keyword.unavailable", "Sadly, that keyword is already in use.");
        }
    }
}
