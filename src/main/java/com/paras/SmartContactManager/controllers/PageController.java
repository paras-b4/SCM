package com.paras.SmartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import com.paras.SmartContactManager.forms.userform;
import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PageController {

    userform Userform =new userform();

    @Autowired
    private UserServices userServices;

    @RequestMapping("/home")
    public String home(Model model)
    {
         model.addAttribute("name","paras yadav");
         model.addAttribute("codinglanguage", "java");
         model.addAttribute("google_link", "https://www.google.com/");
        return "home";
    }
    @RequestMapping("/about")
    public String aboutpage() {
        return "about";
    }
    @RequestMapping("/services")
    public String servicespage() {
        return "services";
    }
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    // @PostMapping("/login") // This is for POST requests to authenticate the user
    // public String processLogin() {
    //     return "redirect:/home"; // Redirect to home after login
    // }

    @GetMapping("/register")
    public String register(Model model) {
        // Userform.setName("paras");

        model.addAttribute("userform", Userform);
        return "register";
    }
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute userform Userform,BindingResult rBindingResult,HttpSession session)
    {

        System.out.println(Userform);
        if(rBindingResult.hasErrors())
        {
            return"register";
        }
        user newuser=new user();
        newuser.setName(Userform.getName());
        newuser.setEmail(Userform.getEmail());
        newuser.setPassword(Userform.getPassword());
        newuser.setAbout(Userform.getAbout());
        newuser.setPhoneNumber(Userform.getPhoneNumber());
        newuser.setProfilepic("https://th.bing.com/th/id/OIP.F7AAZ51YNslUUrejRKkDeQHaE1?w=281&h=183&c=7&r=0&o=5&dpr=1.3&pid=1.7");
        newuser.setEnabled(true);
        newuser.setEmailVerified(true);

        userServices.saveUser(newuser);
        session.setAttribute("message", "Resistration successfull");
        
        return "redirect:/register";

    }

    
    

}
