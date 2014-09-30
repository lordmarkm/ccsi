package com.ccsi.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baldy.commons.security.models.Account;
import com.baldy.commons.security.services.AccountService;
import com.baldy.commons.web.controller.GenericController;
import com.ccsi.security.dto.RegistrationForm;
import com.ccsi.security.validator.RegistrationFormValidator;

@Controller
@RequestMapping("/register")
public class RegistrationController extends GenericController {

    private static Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private ServletContext ctx;

    @Autowired
    private AccountService service;

    @Autowired
    private RegistrationFormValidator validator;

    @RequestMapping(method = GET)
    public ModelAndView register(@RequestParam(required = false) String msg) {
        return mav("register")
                    .addObject("msg", msg);
    }

    @RequestMapping(method = POST)
    public ModelAndView register(@Valid @ModelAttribute RegistrationForm form, BindingResult result) {
        LOG.debug("Registration request. form={}", form);

        if (result.hasErrors()) {
            LOG.debug("Binding errors found; returning to registration form.");
            return mav("register")
                    .addObject("msg", result.getAllErrors().get(0).getDefaultMessage())
                    .addObject("form", form);
        }

        LOG.debug("Creating new account.");

        Account account = new Account();
        account.setUsername(form.getUsername());
        account.setPassword(form.getPassword());
        account.setAuthorities("ROLE_TENANT");

        try {
            service.save(account);
        } catch (PersistenceException h) {
            LOG.debug("Likely username overlap; returning to registration form.");
            return mav("register")
                    .addObject("form", form)
                    .addObject("msg", "Username not available");
        }

        return redirect(ctx.getContextPath() + "/auth/login?msg=reg_success");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
}
