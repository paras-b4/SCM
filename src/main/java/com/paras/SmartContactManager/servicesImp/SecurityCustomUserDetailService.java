package com.paras.SmartContactManager.servicesImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paras.SmartContactManager.repsitories.UserRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {
    
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found with email"));

        
    }
        


}
