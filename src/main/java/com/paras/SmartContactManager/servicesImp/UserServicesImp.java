package com.paras.SmartContactManager.servicesImp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paras.SmartContactManager.helper.AppConstants;
import com.paras.SmartContactManager.helper.ResourceNotFoundException;
import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.repsitories.UserRepo;
import com.paras.SmartContactManager.services.UserServices;
@Service
public class UserServicesImp implements UserServices {

    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void deleteUser(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<user> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Optional<user> getUserById(String id) {
        return repo.findById(id);}
    
    @Override
    public boolean isUserExist(String id) {
        user user=repo.findById(id).orElse(null);
        return user!=null? true : false;

    }

    @Override
    public boolean isUserExistByEmail(String email) {
       user user=repo.findByEmail(email).orElse(null);
       return  user!=null?true:false;

    }

    @Override
    public user saveUser(user user) {
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRolelist(List.of(AppConstants.ROLE_USER));
        return repo.save(user);

    }

    @Override
    public Optional<user> updateUser(user user) {
      user user1= repo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
      user1.setName(user.getName());
      user1.setEmail(user.getEmail());
      user1.setPassword(user.getPassword());
      user1.setAbout(user.getAbout());
      user1.setPhoneNumber(user.getPhoneNumber());
      user1.setProfilepic(user.getProfilepic());
      user1.setEnabled(user.isEnabled());
      user1.setEmailVerified(user.isEmailVerified());
      user1.setPhoneVerified(user.isPhoneVerified());
      user1.setProvider(user.getProvider());
      user1.setProviderUserId(user.getProviderUserId());
      user save=repo.save(user);
      return Optional.ofNullable(save);
   
    }

    @Override
    public user getUserByEmail(String email) {

        return repo.findByEmail(email).orElse(null);
    }

    



}
