package com.pfe.Bank.service;

import com.pfe.Bank.dto.UserDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) throws MissingEntity {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new MissingEntity("User not found with id : " + id);
        }
        return optional.get();
    }

    @Override
    public User assignRolesToUser(long userId, Set<Role> roles) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getRoles().addAll(roles);
            user = userRepository.save(user);
        }

        return user;
    }

    @Override
    public UserDto getUserWithRoles(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserDto(user);
        }
        return null;
    }

    @Override
    public List<User> searchByUsername(String name){
        return userRepository.searchByUsernameLike("%"+name+"%");
    }
}