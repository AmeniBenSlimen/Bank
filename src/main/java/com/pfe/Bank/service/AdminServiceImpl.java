package com.pfe.Bank.service;

import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchByUsername(String name){
        return userRepository.searchByUsernameLike("%"+name+"%");
    }
}