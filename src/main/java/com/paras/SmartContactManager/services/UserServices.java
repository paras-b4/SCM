package com.paras.SmartContactManager.services;

import java.util.List;
import java.util.Optional;

import com.paras.SmartContactManager.model.user;

public interface UserServices {
    user saveUser(user user);
    Optional<user> getUserById(String id);
    Optional<user> updateUser(user user);
    void deleteUser(String id);
    boolean isUserExist(String id);
    boolean isUserExistByEmail(String email);
    List<user> getAllUsers();
    user getUserByEmail(String email);
    



}
