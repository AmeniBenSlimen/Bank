package com.pfe.Bank.controller;

import com.pfe.Bank.dto.UserDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {
    @Autowired
    AdminService adminService;
    //@PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping("/users")
    List<UserDto> getAll(){
        List<User> users = adminService.getAllUsers();
        return UserDto.of(users);
    }

    @GetMapping("/getByUserId/{id}")
    public UserDto getUserId(@PathVariable Long id) throws MissingEntity {
        User user = adminService.getUserById(id);
        return UserDto.of(user);
    }
    @PutMapping("/{userId}/roles")
    public void assignRolesToUser(@PathVariable long userId, @RequestBody Set<Role> roles) {
        adminService.assignRolesToUser(userId, roles);
    }
    @GetMapping("/userRole/{userId}")
    public ResponseEntity<UserDto> getUserWithRoles(@PathVariable Long userId) {
        UserDto userDto = adminService.getUserWithRoles(userId);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/search")
    public List<User> searchByUsername(@RequestParam(name = "name") String name){
        List<User> users = adminService.searchByUsername(name);
        return users;
    }
}
