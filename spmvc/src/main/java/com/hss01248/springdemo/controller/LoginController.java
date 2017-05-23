package com.hss01248.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

@Controller
public class LoginController {


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@RequestParam("usename") String  username,@RequestParam("password") String  password,ModelMap modelMap){

        String str = "name=---- "+username+"---pw=-------"+password;
        modelMap.addAttribute("info",str);
        return "LoginSuccess";

    }
}
