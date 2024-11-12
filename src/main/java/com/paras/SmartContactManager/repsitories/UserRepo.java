package com.paras.SmartContactManager.repsitories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paras.SmartContactManager.model.user;
@Repository
public interface UserRepo extends JpaRepository<user,String> {

    Optional<user> findByEmail(String email);
    


}
