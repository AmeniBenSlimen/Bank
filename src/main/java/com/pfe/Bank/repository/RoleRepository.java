package com.pfe.Bank.repository;

import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);


}
