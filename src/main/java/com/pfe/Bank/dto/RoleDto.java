package com.pfe.Bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.model.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDto extends RoleForm {
    private Long id;


    public static RoleDto of(Role role){
        return new RoleDto(role);
    }

    public RoleDto(Role role) {
        super(role);
        this.id = role.getId();
    }

    public static List<RoleDto> of(List<Role> roles){
        return roles.stream().map(RoleDto::of).collect(Collectors.toList());
    }
}