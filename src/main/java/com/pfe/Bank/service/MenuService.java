package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.form.ModulForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;

import java.util.List;
import java.util.Map;

public interface MenuService {
    Modul findByCodmodule(String codmodule) throws MissingEntity;

    public Menu addMenu(MenuForm form) throws MissingEntity;
    public List<Menu> getAllMenus();
    public Menu findByCodmenu(String codMenu) throws MissingEntity;
    public Menu updateMenu(String codMenu, MenuForm form) throws MissingEntity;
    public Map<String,Boolean> deleteMenu(String codMenu) throws MissingEntity;


}
