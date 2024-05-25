package com.pfe.Bank.load.response;

import java.util.List;

public class JwtRespons {
    private String token;
    private String type = "Bearer ";
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullname;
    private List<String> role;

    public JwtRespons(String token, Long id, String username, String email, String phone, String fullname, List<String> role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.role = role;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
