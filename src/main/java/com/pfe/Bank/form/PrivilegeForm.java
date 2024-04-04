package com.pfe.Bank.form;

import com.pfe.Bank.model.Privilege;
import com.pfe.Bank.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeForm {
    private Long id;
    private String cdRole;
    private String cdMenu;
    private List<Role> roles;
    private List<Module> modules;
    public PrivilegeForm(Privilege privilege){
        this.id=privilege.getId();
    }
}

