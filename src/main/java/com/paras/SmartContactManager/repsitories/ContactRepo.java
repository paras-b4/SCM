package com.paras.SmartContactManager.repsitories;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.paras.SmartContactManager.model.Contact;
import com.paras.SmartContactManager.model.user;

import java.util.List;


@Repository
public interface ContactRepo extends  JpaRepository<Contact,String>{

    Page<Contact> findByUser(user user,PageRequest pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);


    Page<Contact> findByUserAndNameContaining(user user,String nameKeyword, PageRequest pageable);
    Page<Contact> findByUserAndEmailContaining(user user,String emailKeyword , PageRequest pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(user user,String phoneNumberKeyword ,PageRequest pageable);
    


}
