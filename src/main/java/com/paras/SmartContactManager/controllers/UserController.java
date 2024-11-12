package com.paras.SmartContactManager.controllers;

import java.security.Principal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paras.SmartContactManager.helper.Helper;
import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.services.UserServices;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user")
public class UserController {

    // @ModelAttribute
    // public void addLoggedInUserInformation(Model model,Authentication authentication)
    // {
        
    //     String username=Helper.getEmailOfLoggedInUser(authentication);
    //     System.out.println(username);
    //     logger.info("user logged in:{} ",username);
    //     user user=userservice.getUserByEmail(username);
    //     System.out.println(user.getName());
    //     System.out.println(user.getEmail());

    //     model.addAttribute("loggedIn", user);
    // }

    private Logger logger=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServices userservice;

    @RequestMapping("/dashboard")
    public String userDashboard()
    {
        return "user/dashboard";
    }


    @RequestMapping("/profile")
    public String userProfile(Model model ,Authentication authentication) {

        // String username=Helper.getEmailOfLoggedInUser(authentication);
        // System.out.println(username);
        // logger.info("user logged in:{} ",username);
        // user user=userservice.getUserByEmail(username);
        // System.out.println(user.getName());
        // System.out.println(user.getEmail());

        // model.addAttribute("loggedIn", user);

        return "user/profile";


        
        
        
        
    }
    
}
