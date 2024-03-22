package com.pfe.Bank.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.pfe.Bank.model.User;

public class UserDetailsImpl implements UserDetails {

    private long id;
    private  String username;
    private String email;
    private String fullname;
    private String phone;
    private Boolean status=false;



    @JsonIgnore
    private String password;



    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(long id, String username, String fullname,String phone,Boolean status,String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.fullname=fullname;
        this.phone=phone;
        this.email = email;
        this.password = password;
        this.status=status;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user){
        // list des droits d'accés accordés à l'utilisateur
        List<GrantedAuthority> authoroties = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return  new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getPhone(),
                user.getStatus(),
                user.getEmail(),
                user.getPassword(),
                authoroties
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}
