package com.pfe.Bank.dto;

import com.pfe.Bank.model.Privilege;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivilegeDto {

    private long id;
    private String roleId;
    private RoleDto role;
    private String menuId;
    private MenuDto menu;

    public PrivilegeDto(Privilege privilege){
        this.id = privilege.getId();
        this.roleId = privilege.getRole().getName().toString();
        this.role = RoleDto.of(privilege.getRole());
        this.menuId = privilege.getMenu().getCodmenu();
        this.menu=MenuDto.of(privilege.getMenu());
    }

}
