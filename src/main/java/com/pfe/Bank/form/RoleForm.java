package com.pfe.Bank.form;

import com.pfe.Bank.model.ERole;
import com.pfe.Bank.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleForm {
    private String cdRole;
    private ERole lbRole;

    public RoleForm (Role role){
        this.cdRole=role.getCodrole();
        this.lbRole=role.getName();
    }
}
