package com.paras.SmartContactManager.controllers;



import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.paras.SmartContactManager.forms.ContactForm;
import com.paras.SmartContactManager.forms.ContactSearchForm;
import com.paras.SmartContactManager.helper.AppConstants;
import com.paras.SmartContactManager.helper.Helper;
import com.paras.SmartContactManager.model.Contact;
import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.services.ContactService;
import com.paras.SmartContactManager.services.ImageService;
import com.paras.SmartContactManager.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user/contact")
public class contactController2
{
    private Logger logger = LoggerFactory.getLogger(contactController2.class);
    

    @Autowired
    
    private UserServices userServices;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/add")
    public String addContactView(Model model)
    {
        //contactform.setName("Paras Yadav");
        //contactform.setFavorite(true);
        ContactForm contactForm = new ContactForm();
        
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }
   
    @PostMapping("/add")
public String saveContact(
        @Valid @ModelAttribute ContactForm contactForm,
        BindingResult result,
        Authentication authenticate,
        Model model,
        HttpSession session) {

    // Check for validation errors
    if (result.hasErrors()) {
        return "user/add_contact";
    }

    // Get logged-in user's email and user entity
    String username = Helper.getEmailOfLoggedInUser(authenticate);
    user user = userServices.getUserByEmail(username);

    // Log file information, if available
    MultipartFile profilePic = contactForm.getProfilepic();
    if (profilePic != null && !profilePic.isEmpty()) {
        logger.info("File information : {}", profilePic.getOriginalFilename());
    } else {
        logger.info("No profile picture uploaded.");
    }

    // Upload the image if present, otherwise set fileURL to null or default image URL
    String fileURL = (profilePic != null && !profilePic.isEmpty()) 
            ? imageService.UploadImage(profilePic) 
            : null; // or "default_image_url" if you prefer

    // Map form data to the Contact entity
    Contact contact = new Contact();
    contact.setName(contactForm.getName());
    contact.setEmail(contactForm.getEmail());
    contact.setAddress(contactForm.getAddress());
    contact.setDescription(contactForm.getDescription());
    contact.setPhoneNumber(contactForm.getPhoneNumber());
    contact.setFavorite(contactForm.isFavorite());
    contact.setLinkedInLink(contactForm.getLinkedlin());
    contact.setWebsiteLink(contactForm.getWebsiteLink());
    contact.setUser(user);
    contact.setPicture(fileURL); // Sets to null or image URL if uploaded

    // Save contact
    contactService.add(contact);
    session.setAttribute("message", "You have successfully added a new contact");

    return "redirect:/user/contact/add";
}

    @GetMapping
    public String viewContact(@RequestParam(value = "page",defaultValue = "0")int page,
                                @RequestParam(value = "size",defaultValue = "5")int size,
                                @RequestParam(value = "sortBy",defaultValue = "name")String sortBy,
                                @RequestParam(value = "direction",defaultValue = "asc") String direction,  Model model,Authentication authentication)
    {
        String username=Helper.getEmailOfLoggedInUser(authentication);

        user user=userServices.getUserByEmail(username);

        Page<Contact> pagecontact=contactService.getByUser(user,page,size,sortBy,direction);
        
        model.addAttribute("pagecontact", pagecontact);
        model.addAttribute("pagesize", AppConstants.PAGE_SIZE);

        return"user/contact";

    }

    @GetMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        // @RequestParam(value = "field", required = false, defaultValue = "defaultField") String field,
        // @RequestParam(value = "keyword", required = false, defaultValue = "") String value,
        @RequestParam(value="size",defaultValue = AppConstants.PAGE_SIZE +"")int size,
        @RequestParam(value="page",defaultValue = "0")int page,
        @RequestParam(value="sortBy",defaultValue = "name")String sortBy,
        @RequestParam(value="direction",defaultValue = "asc")String direction,
        Model model,Authentication authentication)   {

        logger.info("field {} Keyword {}",contactSearchForm.getField() ,contactSearchForm.getValue());

        var user=userServices.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        //contactService.search(value, field, value);
        Page<Contact> pagecontact=null;
        if(contactSearchForm.getField().equalsIgnoreCase("name"))
        {
             pagecontact=contactService.searchByname(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email"))
        {
            pagecontact=contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
        {
            System.out.println("hello world");
            pagecontact=contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        logger.info("PageContact: {}", pagecontact);
        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pagecontact", pagecontact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deletecontact(@PathVariable String contactId) {

        contactService.deleteById(contactId);
        logger.info("contact {} deleted ",contactId);
        return "redirect:/user/contact";
    }

    @RequestMapping("/view/{contactId}")
    public String updatecontactFormView(@PathVariable("contactId")String contactId,Model model){

        var contact=contactService.getById(contactId);
        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setAddress(contact.getAddress());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setLinkedlin(contact.getLinkedInLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setPicture(contact.getPicture());




        model.addAttribute("contactForm", contactForm);

        return "user/update_contact_view";
        
    }

    @RequestMapping("/update/{contactId}")
    public String updatecontact(@PathVariable String contactId ,Model model,@ModelAttribute ContactForm contactForm){

        var con=new Contact();
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setAddress(contactForm.getAddress());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setFavorite(contactForm.isFavorite());
        con.setLinkedInLink(contactForm.getLinkedlin());
        con.setDescription(contactForm.getDescription());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        //con.setPicture(contactForm.getPicture());

        
        if (contactForm.getProfilepic() != null && !contactForm.getProfilepic().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getProfilepic(), fileName);
           // con.setPicture(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

         var updatecon=contactService.update(con);
         logger.info("update contact {} :",updatecon);
        return "redirect:/user/contact/view/" + contactId;

    }

    
    

}
