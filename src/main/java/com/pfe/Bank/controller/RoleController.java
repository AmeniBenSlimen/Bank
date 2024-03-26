package com.pfe.Bank.controller;

import com.pfe.Bank.dto.MenuDto;
import com.pfe.Bank.dto.ModulDto;
import com.pfe.Bank.dto.RoleDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.ModulForm;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
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
    @DeleteMapping("/deleteRole/{id}")
    public Map<String,Boolean> deleteRole(@PathVariable Long id) throws MissingEntity{
        return roleService.deleteRole(id);
    }
}
