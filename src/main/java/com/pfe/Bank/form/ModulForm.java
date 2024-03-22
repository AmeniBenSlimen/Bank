package com.pfe.Bank.form;

import com.pfe.Bank.model.Modul;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModulForm {
    private String cdModul;
    private String lbModul;


    public ModulForm(Modul modul){
        this.cdModul=modul.getCodmodule();
        this.lbModul=modul.getLibmodule();
    }
}
