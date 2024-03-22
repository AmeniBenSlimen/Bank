package com.pfe.Bank.form;

import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.model.Role;

import java.util.List;

public class PrivilegeForm {
    private List<Role> roles;
    private List<Modul> modules;



    private List<Menu> menus;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



    public PrivilegeForm() {}

    public List<Modul> getModules() {
        return modules;
    }

    public void setModules(List<Modul> modules) {
        this.modules = modules;
    }

    public PrivilegeForm(List<Role> roles, List<Modul> modules, List<Menu> menus) {
        this.roles = roles;
        this.modules = modules;
        this.menus = menus;
    }
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}

