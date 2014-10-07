package com.ccsi.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.security.reference.Roles;

/**
 * @author mbmartinez
 */
@Controller
@RequestMapping("/auth")
public class LoginController extends GenericController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = GET)
    public ModelAndView login(@RequestParam(required=false) String msg) {
        return mav("login")
            .addObject("msg", msg);
    }

    @RequestMapping("/redirect")
    public String redirect(Principal principal, HttpServletRequest request) {
        LOG.debug("Redirecing user. principal={}", principal);

//        if (request.isUserInRole(Roles.asRole(Roles.ADMIN))) {
//            return "redirect:/manage";
//        } else {
            return "redirect:/";
//        }
    }
}
