package com.pfe.Bank.controller;

import com.pfe.Bank.load.request.LoginRequest;
import com.pfe.Bank.load.request.SignupRequest;
import com.pfe.Bank.load.response.JwtRespons;
import com.pfe.Bank.load.response.JwtResponse;
import com.pfe.Bank.load.response.MessageResponse;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.RoleRepository;
import com.pfe.Bank.repository.UserRepository;
import com.pfe.Bank.security.jwt.JwtUtils;
import com.pfe.Bank.security.services.UserDetailsImpl;

import com.pfe.Bank.service.AdminService;
import com.pfe.Bank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AuthenticationRest {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AdminService adminService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request){
        System.out.println("Received signup request: " + request);

            if(userRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest()
                    .body(
                            new MessageResponse("Error : Email is already in use !!!!")
                    );
        if(userRepository.existsByUsername(request.getUsername()))
            return ResponseEntity.badRequest()
                    .body(
                            new MessageResponse("Error : Username is already in use !!!!")
                    );


        User user = new User(
                request.getUsername(),
                request.getFullname(),
                request.getPhone(),
                request.getStatus(),
                request.getEmail(),
                encoder.encode(request.getPassword()));

        Set<String> subroles = request.getRole();

        Set<Role> roles = new HashSet<>();

        if(subroles == null){
            Role userrole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error : role is not found"));
            roles.add(userrole);
        }
        else{

            subroles.forEach(
                    role -> {

                        switch(role){
                            case "admin":
                                Role roleadmin = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error : role is not found"));
                                roles.add(roleadmin);
                            case "secretaire":
                                Role rolesec = roleRepository.findByName(ERole.ROLE_MANAGER)
                                        .orElseThrow(()-> new RuntimeException("Error : role is not found"));
                                roles.add(rolesec);
                            default:
                                // le role par défaut
                                Role userrole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(()-> new RuntimeException("Error : role is not found"));
                                roles.add(userrole);
                        }
                    }

            );


        }
        user.setStatus(false);
        user.setRoles(roles);
        userRepository.save(user);
        String adminEmail = "afifimajd383@gmail.com";
        String subject = "Nouvelle inscription - Activation du compte";
        String activationLink = "http://localhost:4200/admin/list-users";
        String message = "Un nouvel utilisateur s'est inscrit.\n\n" +
                "Nom d'utilisateur: " + user.getUsername() + "\n" +
                "Email: " + user.getEmail() + "\n\n" +
                "Veuillez activer ce compte en cliquant sur le lien suivant :\n" +
                activationLink;

        emailService.sendEmail(adminEmail, subject, message);
        return  ResponseEntity.ok(new MessageResponse("User Registred successfully !!!!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> auth_user(@Valid @RequestBody LoginRequest loginRequest){

        // pour l'authentification (vérifier l'existance de l'utilisateur)

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication); // traiter l'user selon ses droits d'accées

        String jwt = jwtUtils.generateJwtToken(authentication); // génération du token

        UserDetailsImpl userdetails = (UserDetailsImpl) authentication.getPrincipal(); // get l'utilisateur principal

        // récupérer la list des roles

        List<String> roles = userdetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userdetails.getId(),
                        userdetails.getUsername(),
                        userdetails.getEmail(),
                        roles));
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        SecurityContextHolder.getContext().setAuthentication(authentication); // traiter l'user selon ses droits d'accées

        String jwt = jwtUtils.generateJwtToken(authentication); // génération du token

        UserDetailsImpl userdetails = (UserDetailsImpl) authentication.getPrincipal(); // get l'utilisateur principal

        // récupérer la list des roles
        List<String> roles = userdetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtRespons(
                        jwt,
                        userdetails.getId(),
                        userdetails.getUsername(),
                        userdetails.getEmail(),
                        userdetails.getPhone(),
                        userdetails.getFullname(),
                        roles));
    }


}

