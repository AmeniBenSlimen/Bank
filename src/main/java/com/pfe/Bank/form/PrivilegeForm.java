package com.pfe.Bank.form;

import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.model.Privilege;
import com.pfe.Bank.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PrivilegeForm {
    private long id ;
    private long roleId;
    private String menuId;
    public PrivilegeForm(Privilege privilege){
        this.id=privilege.getId();

    }

}

