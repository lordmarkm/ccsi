package com.ccsi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baldy.commons.web.controller.GenericController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(method = GET)
public class RootController extends GenericController {

    @RequestMapping("/")
    public ModelAndView tenant() {
        return mav("tenant");
    }

}
