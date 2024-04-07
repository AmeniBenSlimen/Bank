package com.pfe.Bank.repository;


import com.pfe.Bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username); // authentification

    Optional<User> findByEmail(String email); // authentification

    Boolean existsByUsername(String username); //subscribe

    Boolean existsByEmail(String email); //subscribe

    Optional<User> findByUsernameAndStatus(String username, Boolean status);

    public List<User> searchByUsernameLike(String name);
   // List<User> findById(Long userId);



}