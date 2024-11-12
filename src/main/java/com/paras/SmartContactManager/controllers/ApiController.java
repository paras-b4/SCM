package com.paras.SmartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paras.SmartContactManager.model.Contact;
import com.paras.SmartContactManager.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    // @GetMapping("/contact/{contactId}")
    // public Contact getContact(@PathVariable String contactId)
    // {
    //     return contactService.getById(contactId);
        
    // }
    @GetMapping("/contact/{contactId}")
    public ResponseEntity<Contact> getContact(@PathVariable String contactId) {
        Contact contact = contactService.getById(contactId);
        if (contact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(null);
        }
        return ResponseEntity.ok(contact);
}


}
