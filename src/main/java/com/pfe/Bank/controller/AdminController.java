package com.pfe.Bank.controller;

import com.pfe.Bank.dto.UserDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.UserForm;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @DeleteMapping("/user/{userId}/roles/{roleId}")
    public ResponseEntity<?> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        adminService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public List<User> searchByUsername(@RequestParam(name = "name") String name){
        List<User> users = adminService.searchByUsername(name);
        return users;
    }
    @PutMapping("/updateUser/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @Valid @RequestBody UserForm form) throws MissingEntity{
        User user = adminService.updateUser(userId,form);
        return UserDto.of(user);

    }
    @DeleteMapping("/deleteUser/{userId}")
    public Map<String,Boolean> deleteUser(@PathVariable Long userId) throws MissingEntity{
        return adminService.deleteUser(userId);
    }
    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> userOpt = adminService.findByUsername(userDetails.getUsername());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(UserDto.of(userOpt.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable Long id) {
        User activatedUser = adminService.activateUser(id);
        return ResponseEntity.ok(activatedUser);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable Long id) {
        User deactivatedUser = adminService.deactivateUser(id);
        return ResponseEntity.ok(deactivatedUser);
    }
}
