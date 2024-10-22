package com.pfe.Bank.security.services;

import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Chargement des détails de l'utilisateur pour : {}"+ username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("Utilisateur non trouvé avec le nom d'utilisateur : {}"+ username);
                    return new UsernameNotFoundException("Utilisateur non trouvé");
                });

        if (!user.getStatus()) {
            System.out.println("Le compte de l'utilisateur est désactivé pour le nom d'utilisateur : {}"+ username);
            throw new DisabledException("Le compte de l'utilisateur est désactivé");
        }

        System.out.println("Utilisateur trouvé : {}"+ username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }



    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }
}