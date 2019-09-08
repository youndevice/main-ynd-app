package com.youndevice.app.controller;

import com.youndevice.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeviceController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/device/list"}, method = RequestMethod.GET)
    public ModelAndView listDevices() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deviceListing");
        return modelAndView;
    }

}