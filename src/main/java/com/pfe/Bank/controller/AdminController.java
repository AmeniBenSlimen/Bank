package com.pfe.Bank.controller;

import com.pfe.Bank.model.User;
import com.pfe.Bank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {
    @Autowired
    AdminService adminService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @GetMapping("/search")
    public List<User> searchByUsername(@RequestParam(name = "name") String name){
        List<User> users = adminService.searchByUsername(name);
        return users;
    }
}
