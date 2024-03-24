package com.pfe.Bank.controller;

import com.pfe.Bank.load.request.LoginRequest;
import com.pfe.Bank.load.request.SignupRequest;
import com.pfe.Bank.load.response.JwtResponse;
import com.pfe.Bank.load.response.MessageResponse;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.RoleRepository;
import com.pfe.Bank.repository.UserRepository;
import com.pfe.Bank.security.jwt.JwtUtils;
import com.pfe.Bank.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request){

        //email unique
        if(userRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest()
                    .body(
                            new MessageResponse("Error : Email is already in use !!!!")
                    );
        //username unique
        if(userRepository.existsByUsername(request.getUsername()))
            return ResponseEntity.badRequest()
                    .body(
                            new MessageResponse("Error : Username is already in use !!!!")
                    );

        // cryptage du password

        User user = new User(
                request.getUsername(),
                request.getFullname(),
                request.getPhone(),
                request.getStatus(),
                request.getEmail(),
                encoder.encode(request.getPassword()));

        // la liste des roles
        Set<String> subroles = request.getRole(); // récupérer les roles (String) as input

        Set<Role> roles = new HashSet<>();  // liste des roles à accorder ---> output

        if(subroles == null){
            // le role par défaut (role user)
            Role userrole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error : role is not found"));
            roles.add(userrole);
        }
        else{
            // un ensemble de role à traiter
            // for each role in subroles
            subroles.forEach(
                    role -> {

                        switch(role){
                            case "admin":
                                Role roleadmin = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error : role is not found"));
                                roles.add(roleadmin);
                            case "secretaire":
                                Role rolesec = roleRepository.findByName(ERole.ROLE_SECRETAIRE)
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
        user.setRoles(roles); // accorder la liste des Roles
        userRepository.save(user); // enregistrer dans la base

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
}

