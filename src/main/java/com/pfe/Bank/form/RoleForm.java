package com.pfe.Bank.form;

import com.pfe.Bank.dto.RoleDto;
import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class RoleForm {
    private long id;
    private String cdRole;
    private ERole lbRole;
    public RoleForm() {
    }
    public RoleForm (Role role){
        this.id= role.getId();
        this.cdRole=role.getCodrole();
        this.lbRole=role.getName();
    }

    
}
