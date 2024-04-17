package com.pfe.Bank.controller;

import com.pfe.Bank.dto.RoleDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class RoleController {
    @Autowired
    RoleService roleService;
    @PostMapping("/addRole")
    public RoleDto addRole(@RequestBody RoleForm form) throws MissingEntity {
        Role role =roleService.addRole(form);
        return RoleDto.of(role);
    }
    @GetMapping("/getAllRoles")
    List<RoleDto> getAllRoles(){
        List<Role> role = roleService.getAllNtRole();
        return RoleDto.of(role);
    }
    @GetMapping("/getByIdRole/{id}")
    public RoleDto getRoleId(@PathVariable Long id) throws MissingEntity{
        Role role = roleService.getRoleById(id);
        return RoleDto.of(role);
    }
    @PutMapping("/updateRole/{id}")
    public RoleDto updateRole(@PathVariable Long id, @RequestBody RoleForm form) throws MissingEntity{
        Role role = roleService.updateRole(id,form);
        return RoleDto.of(role);
    }
    @DeleteMapping("/deleteRole/{roleId}")
    public Map<String,Boolean> deleteRole(@PathVariable Long roleId) throws MissingEntity{
        return roleService.deleteRole(roleId);
    }
    @GetMapping("/getByNameRole/{name}")
    public RoleDto getRoleName(@PathVariable String name) throws MissingEntity{
        Role role = roleService.findByName(ERole.valueOf(name));
        return RoleDto.of(role);
    }
}
