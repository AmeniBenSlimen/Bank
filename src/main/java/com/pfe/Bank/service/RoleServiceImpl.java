package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllNtRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role addRole(RoleForm form) throws MissingEntity {
        Role role = new Role();
        role.setCodrole(form.getCdRole());
        role.setName(form.getLbRole());
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, RoleForm form) throws MissingEntity {
        Role role = getRoleById(id);
        role.setCodrole(form.getCdRole());
        role.setName(form.getLbRole());
        return roleRepository.save(role);
    }

    @Override
    public Map<String, Boolean> deleteRole(Long roleId) throws MissingEntity{
        /* facon 1 getAll Privileges and then delete
        privilegeRepository.deleteAll(privilegeRepository.findAllByRoleId(roelId));*/

        /* façon 2 on testesi le roel est utilisé
        boolean isUsed  = privilegeRepository.existsById(roelId) ;
        if(isUsed)
        {
            throw new IllegalArgumentException("you cant delete this rolz") ;
        } else {
            // delete roel
        }*/

        Role role = getRoleById(roleId);
        roleRepository.delete(role);
        Map<String,Boolean> map = new HashMap<>();
        map.put("deleted",Boolean.TRUE);
        return map;
    }

    @Override
    public void saveNtRole(Role ntRole) {
        this.roleRepository.save(ntRole);
    }

    @Override
    public Role getRoleById(long id) throws MissingEntity{
        Optional<Role> optional = roleRepository.findById(id);
        if(!optional.isPresent()){
            throw new MissingEntity("Role not found with code Menu : "+id);
        }
        return optional.get();
    }

    @Override
    public Role findByName(ERole name) throws MissingEntity {
        Optional<Role> optional = roleRepository.findByName(name);
        if(!optional.isPresent()){
            throw new MissingEntity("RoleName not found with Name : "+name);
        }
        return optional.get();
    }

    @Override

    public Page<Role> findPaginatedNtRole(int pageNoNtRole, int pageSize, String sortField, String sortDirection) {
        {
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();

            Pageable pageable = PageRequest.of(pageNoNtRole - 1, pageSize, sort);
            return this.roleRepository.findAll(pageable);
        }
    }
}

