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

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PrivilegeController {
    @Autowired
    PrivilegeService privilegeService;
    /*@GetMapping("/displayForm")
    public PrivilegeForm displayForm() {
        return privilegeService.displayForm();
    }*/
    @PostMapping("/addPrivilege")
    public PrivilegeDto addPrivilege(@RequestBody PrivilegeForm form) throws MissingEntity {
        Privilege privilege =privilegeService.addPrivilege(form);
        return PrivilegeDto.of(privilege);
    }
    @GetMapping("/privilege/form")
    public ResponseEntity<PrivilegeForm> displayForm() throws DuplicateEntity {
        PrivilegeForm privilegeForm = privilegeService.displayForm();
        return new ResponseEntity<>(privilegeForm, HttpStatus.OK);
    }



}