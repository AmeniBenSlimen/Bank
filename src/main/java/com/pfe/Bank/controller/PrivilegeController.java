package com.pfe.Bank.controller;

import com.pfe.Bank.dto.MenuDto;
import com.pfe.Bank.dto.PrivilegeDto;
import com.pfe.Bank.exception.DuplicateEntity;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Privilege;
import com.pfe.Bank.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PrivilegeController {
    @Autowired
    PrivilegeService privilegeService;

    @PostMapping("/add")
    public ResponseEntity<?> addPrivilege(@RequestParam long roleId, @RequestParam String menuId) {
        return ResponseEntity.ok(new PrivilegeDto(privilegeService.addPrivilege(roleId, menuId)));
    }

    @DeleteMapping("/deletePrivilege/{id}")
    public void deletePrivilege(@PathVariable long id) {
        privilegeService.deletePrivilege(id);

    }
    @GetMapping("/all")
    public ResponseEntity<List<PrivilegeDto>> getAllPrivileges() {
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        if (privileges.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PrivilegeDto> privilegedtos = privileges.stream().map(PrivilegeDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(privilegedtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeDto> getPrivilegeById(@PathVariable long id) {
        Privilege privilege = privilegeService.getPrivilegeById(id);
        if (privilege == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PrivilegeDto(privilege), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PrivilegeDto> updatePrivilege(@PathVariable long id, @RequestParam Long roleId, @RequestParam String menuId) {
        Privilege updatedPrivilege = privilegeService.updatePrivilege(id, roleId, menuId);

        if (updatedPrivilege == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new PrivilegeDto(updatedPrivilege), HttpStatus.OK);
    }
    @GetMapping("/privilegesByRole/{roleId}")
    public ResponseEntity<List<PrivilegeDto>> getPrivilegesByRole(@PathVariable long roleId) {
        List<Privilege> privileges = privilegeService.getPrivilegesByRole(roleId);
        if (privileges.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PrivilegeDto> privilegeDto = privileges.stream().map(PrivilegeDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(privilegeDto, HttpStatus.OK);
    }

}