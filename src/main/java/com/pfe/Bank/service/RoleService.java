package com.pfe.Bank.service;

import com.pfe.Bank.model.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    List<Role> getAllNtRole();

    Page<Role> findPaginatedNtRole(int pageNoNtRole, int pageSize, String sortField, String sortDirection);

    void saveNtRole(Role ntRole);

    Role getRoleById(long id);
}
