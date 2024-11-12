package com.paras.SmartContactManager.services;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.paras.SmartContactManager.model.Contact;
import com.paras.SmartContactManager.model.user;

@Service
public interface ContactService {

    Contact add(Contact contact);
    Contact update(Contact contact);
    List<Contact> getall();
    Contact getById(String id);
    void deleteById(String id); 
    Page<Contact> searchByname(String nameKeyword ,int size,int page,String sortBy ,String order,user user);
    Page<Contact> searchByEmail(String emailKeyword ,int size,int page,String sortBy ,String order,user user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword ,int size,int page,String sortBy ,String order,user user);
    List<Contact> findByUserId(String UserId);
    Page<Contact> getByUser(user user,int page,int size,String sortField,String sortDirection);

    

    
} 
