package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Role> getAllNtRole();
    public Role addRole(RoleForm form) throws MissingEntity;
    public Role updateRole(Long id, RoleForm form) throws MissingEntity;

    public Map<String,Boolean> deleteRole(Long roleId) throws MissingEntity;

    Page<Role> findPaginatedNtRole(int pageNoNtRole, int pageSize, String sortField, String sortDirection);

    void saveNtRole(Role ntRole);

    Role getRoleById(long id) throws MissingEntity;
    public Role findByName(ERole name) throws MissingEntity;

}
