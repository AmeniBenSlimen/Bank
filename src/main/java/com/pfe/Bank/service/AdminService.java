package com.pfe.Bank.service;

import com.pfe.Bank.dto.UserDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.UserForm;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface AdminService {
    List<User> getAllUsers();
    User getUserById(long id) throws MissingEntity;
    User assignRolesToUser(long userId, Set<Role> roles);

    public List<User> searchByUsername(String name);
    UserDto getUserWithRoles(Long userId);
    void removeRoleFromUser(Long userId, Long roleId);
    public User updateUser(Long userId, UserForm form) throws MissingEntity;
    Map<String,Boolean> deleteUser(long userId) throws MissingEntity;
    public Optional<User> findByUsername(String username);
    User activateUser(Long id);
    User deactivateUser(Long id);
}