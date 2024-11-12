package com.paras.SmartContactManager.servicesImp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.paras.SmartContactManager.forms.ContactForm;
import com.paras.SmartContactManager.helper.ResourceNotFoundException;
import com.paras.SmartContactManager.model.Contact;
import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.repsitories.ContactRepo;
import com.paras.SmartContactManager.services.ContactService;
@Service
public class ContactServiceImp implements ContactService{

    @Autowired
    private ContactRepo repo;

    // @Autowired
    // private Contact contact;



    @Override
    public Contact add(Contact contact) {
        String contactId=UUID.randomUUID().toString();
        contact.setId(contactId);
       return repo.save(contact);
    }

  

    @Override
    public List<Contact> getall() {
       return repo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return repo.findById(id).orElse(null);
    }

    // @Override
    // public List<Contact> search(String name, String email, String phoneNumber) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'search'");
    // }

    @Override
    public List<Contact> findByUserId(String UserId) {
       return repo.findByUserId(UserId);
    }

    @Override
    public void deleteById(String id) {
        Contact contact= repo.findById(id).orElse(null);
        repo.delete(contact);
        
         
    }

    @Override
    public Contact update(Contact contact) {
        var contactOld = repo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact not found "));
            contactOld.setName(contact.getName());
            contactOld.setEmail(contact.getEmail());
            contactOld.setPhoneNumber(contact.getPhoneNumber());
            contactOld.setDescription(contact.getDescription());
            contactOld.setPicture(contact.getPicture());
            contactOld.setLinkedInLink(contact.getLinkedInLink());
            contactOld.setFavorite(contact.isFavorite());
            contactOld.setAddress(contact.getAddress());
            contactOld.setWebsiteLink(contact.getWebsiteLink());


            return repo.save(contactOld);
            



          }



    // @Override
    // public Page<Contact> getByUser(user user,int page,int size,String sortBy,String direction) {
    //     Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
    //     var pageableRequest=PageRequest.of(page,size,sort);
    //    return repo.findByUser(user,pageableRequest);
    // }
    @Override
    public Page<Contact> getByUser(user user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return repo.findByUser(user, pageable);

    }



    @Override
    public Page<Contact> searchByname(String nameKeyword, int size, int page, String sortBy, String order,user user) {

        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);
       
       return repo.findByUserAndNameContaining(user,nameKeyword, pageable);
    }



    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,user user) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);
       
       return repo.findByUserAndEmailContaining(user,emailKeyword,pageable);
    }



    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order,user user) {
                Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
                var pageable=PageRequest.of(page,size,sort);
               
               return repo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword, pageable);
            }



  

}
