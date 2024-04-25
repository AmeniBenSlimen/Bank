package com.pfe.Bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfe.Bank.form.ModulForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ModulDto {

    @JsonProperty("cdModul")
    private String cdModul;

    @JsonProperty("lbModul")
    private String lbModul;

    private List<Menu> menuDtos;

    public static ModulDto of(Modul modul){
        return new ModulDto(modul);
    }

    public ModulDto(Modul modul) {
        this.cdModul = modul.getCodmodule();
        this.lbModul = modul.getLibmodule();
        this.menuDtos = modul.getMenus();
    }

    public static List<ModulDto> of(List<Modul> modules){
        return modules.stream().map(ModulDto::of).collect(Collectors.toList());
    }
}

