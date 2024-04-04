package com.pfe.Bank.dto;

import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Privilege;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PrivilegeDto {
    private ERole cdRole ;
    private RoleDto role ;
    private String cdMenu ;
    private MenuDto menu;
    public static PrivilegeDto of(Privilege privilege){

        return new PrivilegeDto(privilege);
    }
    public PrivilegeDto(Privilege privilege) {
        this.cdRole= ERole.valueOf(privilege.getRole().getCodrole());
        this.cdMenu=privilege.getMenu().getCodmenu();
        this.role=RoleDto.of(privilege.getRole());
        this.menu=MenuDto.of(privilege.getMenu());
    }
    public static List<PrivilegeDto> of(List<Privilege> privileges){
        return privileges.stream().map(PrivilegeDto::of).collect(Collectors.toList());
    }



}
