package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.repository.MenuRepository;
import com.pfe.Bank.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Override
    public Modul findByCodmodule(String codmodule) throws MissingEntity {
        Optional<Modul> module = moduleRepository.findByCodmodule(codmodule);
        if(!module.isPresent()){
            throw new MissingEntity("Module not found with cod Module : "+codmodule);
        }
        return module.get();
    }
    @Override
    public Menu addMenu(MenuForm form) throws MissingEntity{
        Modul modul =findByCodmodule(form.getCdModul());
        Menu menu = new Menu();
        menu.setCodmenu(form.getCdMenu());
        menu.setLibmenu(form.getLbMenu());
        menu.setModule(modul);
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findByCodmenu(String codMenu) throws MissingEntity {
        Optional<Menu> menue = menuRepository.findByCodmenu(codMenu);
        if(!menue.isPresent()){
            throw new MissingEntity("Menue not found with code Menu : "+codMenu);
        }
        return menue.get();
    }

    @Override
    public Menu updateMenu(String codMenu, MenuForm form) throws MissingEntity {
        Menu menu = findByCodmenu(codMenu);
        menu.setCodmenu(form.getCdMenu());
        menu.setLibmenu(form.getLbMenu());
        return menuRepository.save(menu);    }

    @Override
    public Map<String, Boolean> deleteMenu(String codMenu) throws MissingEntity {
        Menu menu = findByCodmenu(codMenu);
        menuRepository.delete(menu);
        Map<String,Boolean> map = new HashMap<>();
        map.put("deleted",Boolean.TRUE);
        return map;
    }
}
