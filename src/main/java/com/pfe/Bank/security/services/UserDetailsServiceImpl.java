package com.pfe.Bank.security.services;

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
    UserRepository agent;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        User user = agent.findByUsernameAndStatus(s,false).orElseThrow(
                ()-> new UsernameNotFoundException("User Not found with username :"+s)
        );

        return UserDetailsImpl.build(user);
    }
}
