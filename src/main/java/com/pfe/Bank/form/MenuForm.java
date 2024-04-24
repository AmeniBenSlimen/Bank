package com.pfe.Bank.form;

import com.pfe.Bank.model.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuForm {
    private String cdMenu ;
    private String lbMenu;
    private String cdModule;
    public MenuForm(Menu menu){
        this.cdMenu=menu.getCodmenu();
        this.lbMenu=menu.getLibmenu();

    }
}