package com.paras.SmartContactManager.controllers;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.paras.SmartContactManager.helper.Helper;
import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.services.UserServices;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserServices userservice;

    private Logger logger=LoggerFactory.getLogger(RootController.class);
     @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication)
    {
        if(authentication==null)
        {
            return;
        }
        
        String username=Helper.getEmailOfLoggedInUser(authentication);
        System.out.println(username);
        logger.info("user logged in:{} ",username);
        user user=userservice.getUserByEmail(username);
        System.out.println(user);
        
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedIn", user);
    }

}
