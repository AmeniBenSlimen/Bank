package com.pfe.Bank.form;

import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.model.Privilege;
import com.pfe.Bank.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



public class PrivilegeForm {
    private List<Role> roles;
    private List<Modul> modules;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Modul> getModules() {
        return modules;
    }

    public void setModules(List<Modul> modules) {
        this.modules = modules;
    }
    public PrivilegeForm() {}

    public PrivilegeForm(List<Role> roles, List<Modul> modules) {
        this.roles = roles;
        this.modules = modules;
    }
    public void setMenus(List<Menu> menus) {
    }

    public void setModule(String moduleId) {
    }


    public String getModule() {
        return null ;

    }

}

