package com.pfe.Bank.repository;

import com.pfe.Bank.model.Privilege;
import com.pfe.Bank.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    List<Privilege> findByRole(Role role);
    List<Privilege> findByRoleId(long roleId);

}
