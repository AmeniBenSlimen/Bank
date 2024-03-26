package com.pfe.Bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfe.Bank.model.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MenuDto {
    @JsonProperty("cdMenu")
    private String cdMenu;

    @JsonProperty("lbMenu")
    private String lbMenu;


    private String cdModule ;
    private ModulDto module ;

    public static MenuDto of(Menu menu){

        return new MenuDto(menu);
    }

    public MenuDto(Menu menu) {
        this.cdMenu=menu.getCodmenu();
        this.lbMenu=menu.getLibmenu();
        this.cdModule=menu.getModule().getCodmodule();
        this.module=ModulDto.of(menu.getModule());

    }

    public static List<MenuDto> of(List<Menu> menus){
        return menus.stream().map(MenuDto::of).collect(Collectors.toList());
    }
}
