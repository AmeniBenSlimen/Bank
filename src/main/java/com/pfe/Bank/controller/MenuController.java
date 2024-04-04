package com.pfe.Bank.controller;

import com.pfe.Bank.dto.MenuDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping("/addMenu")
    public MenuDto addMenu(@RequestBody MenuForm form) throws MissingEntity {
        Menu menu =menuService.addMenu(form);
        return MenuDto.of(menu);
    }
    @GetMapping("/admin/dashboard")
    List<MenuDto> getAllMenus(){
        List<Menu> menu = menuService.getAllMenus();
        return MenuDto.of(menu);
    }
    @GetMapping("/getByCodMenu/{codMenu}")
    public MenuDto getMenu(@PathVariable String codMenu) throws MissingEntity{
        Menu menue = menuService.findByCodmenu(codMenu);
        return MenuDto.of(menue);
    }
    @PutMapping("/updateMenue/{codMenu}")
    public MenuDto updateMenu(@PathVariable String codMenu, @RequestBody MenuForm form) throws MissingEntity{
        Menu menu = menuService.updateMenu(codMenu,form);
        return MenuDto.of(menu);

    }
    @DeleteMapping("/deleteMenue/{codMenu}")
    public Map<String,Boolean> deleteMenu(@PathVariable String codMenu) throws MissingEntity{
        return menuService.deleteMenu(codMenu);
    }
}
