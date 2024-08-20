package com.pfe.Bank.service;

import com.pfe.Bank.dto.UserDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.UserForm;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) throws MissingEntity {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new MissingEntity("User not found with id : " + id);
        }
        return optional.get();
    }

    @Override
    public User assignRolesToUser(long userId, Set<Role> roles) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getRoles().addAll(roles);
            user = userRepository.save(user);
        }

        return user;
    }

    @Override
    public UserDto getUserWithRoles(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserDto(user);
        }
        return null;
    }

    @Override
    public void removeRoleFromUser(Long userId, Long roleId) {
        userRepository.findById(userId).ifPresent(user -> {
            Role roleToRemove = user.getRoles().stream()
                    .filter(role -> role.getId() == roleId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.getRoles().remove(roleToRemove);
            userRepository.save(user);
        });
    }

    @Override
    public User updateUser(Long userId, UserForm form) throws MissingEntity {
        User user = getUserById(userId);
        user.setUsername(form.getUsername());
        user.setFullname(form.getFullname());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setPassword(form.getPassword());
        user.setStatus(form.getStatus());
        return userRepository.save(user);
    }

    @Override
    public Map<String, Boolean> deleteUser(long userId) throws MissingEntity {
        User user = getUserById(userId);
        userRepository.delete(user);
        Map<String,Boolean> map = new HashMap<>();
        map.put("deleted",Boolean.TRUE);
        return map;
    }


    @Override
    public List<User> searchByUsername(String name){
        return userRepository.searchByUsernameLike("%"+name+"%");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        user.setStatus(true);
        return userRepository.save(user);
    }

    @Override
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        user.setStatus(false);
        return userRepository.save(user);
    }
    public User createUser(User user) {
        user.setStatus(false);
        User createdUser = userRepository.save(user);

        String adminEmail = "amenibenslimen9@gmail.com";
        String subject = "Nouvel utilisateur à activer";
        String text = String.format(
                "Un nouvel utilisateur a créé un compte et attend l'activation.\n\n" +
                        "Détails de l'utilisateur:\n" +
                        "ID: %d\n" +
                        "Nom: %s\n" +
                        "Prénom: %s\n" +
                        "E-mail: %s\n\n" +
                        "Veuillez activer son compte en visitant le lien suivant :\n" +
                        "http://localhost:8080/admin/activate/%d",
                createdUser.getId(), createdUser.getUsername(), createdUser.getFullname(), createdUser.getEmail(), createdUser.getId()
        );
        emailService.sendEmail(adminEmail, subject, text);

        return createdUser;
    }
}