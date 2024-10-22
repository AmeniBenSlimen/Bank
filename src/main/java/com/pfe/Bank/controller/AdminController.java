package com.pfe.Bank.controller;

import com.pfe.Bank.dto.UserDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.UserForm;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.service.AdminService;
import com.pfe.Bank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {
    @Autowired
    AdminService adminService;
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Autowired
    EmailService emailService;
    @PreAuthorize("hasRole('ADMIN')")
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
    @PutMapping("/{userId}/activate")
    public ResponseEntity<Map<String, String>> activateUser(@PathVariable Long userId) {
        User user = adminService.activateUser(userId);

        emailService.sendAccountActivatedEmail(user.getEmail(), user.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User activated successfully");
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable Long id) {
        User deactivatedUser = adminService.deactivateUser(id);
        return ResponseEntity.ok(deactivatedUser);
    }
    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, @RequestBody User user) {
        try {
            User updatedUser = adminService.updateUserProfile(userId, user);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user profile");
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String currentUsername = authentication.getName();

        Optional<User> optionalUser = adminService.findByUsername(currentUsername);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserDto userDto = UserDto.of(user);

            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserDto userDto,
            Authentication authentication) {

        String currentUsername = authentication.getName();

        try {
            User user = adminService.findByUsername(currentUsername).orElseThrow(() -> new MissingEntity("User not found"));

            if (user.getId() == id) {
                adminService.updateUser(id, userDto);
                return ResponseEntity.ok("Profile updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this profile");
            }
        } catch (MissingEntity e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
