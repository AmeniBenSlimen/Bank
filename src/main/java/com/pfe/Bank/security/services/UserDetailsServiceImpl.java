package com.pfe.Bank.security.services;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.UserForm;
import com.pfe.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pfe.Bank.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userr = user.findByUsernameAndStatus(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        if (!userr.isStatus()) {
            throw new UsernameNotFoundException("User is not active.");
        }

        return UserDetailsImpl.build(userr);
    }



}
