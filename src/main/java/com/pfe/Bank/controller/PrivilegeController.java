package com.pfe.Bank.controller;

import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PrivilegeController {
    @Autowired
    PrivilegeService privilegeService;
    @GetMapping("/displayForm")
    public PrivilegeForm displayForm() {
        return privilegeService.displayForm();
    }

}