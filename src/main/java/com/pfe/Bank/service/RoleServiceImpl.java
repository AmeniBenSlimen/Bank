package com.pfe.Bank.service;

import com.pfe.Bank.model.Role;
import com.pfe.Bank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void saveNtRole(Role ntRole) {
        this.roleRepository.save(ntRole);
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> optional = roleRepository.findById(id);
        Role ntRole = null;
        if (optional.isPresent()) {
            ntRole = optional.get();
        } else {
            throw new RuntimeException(" role ntf not found for id :: " + id);
        }
        return ntRole;
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

