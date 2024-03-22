package com.pfe.Bank.service;

import com.pfe.Bank.model.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    public List<User> searchByUsername(String name);
}