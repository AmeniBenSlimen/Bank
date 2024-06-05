package com.pfe.Bank.dto;

import com.pfe.Bank.form.ModeleForm;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class ModeleDto extends ModeleForm {
    public ModeleDto(Modele modele) {
        super(modele);
    }

    public static ModeleDto of(Modele modele){
        return new ModeleDto(modele);
    }

    public static List<ModeleDto> of(List<Modele> modeles){
        return modeles.stream().map(ModeleDto::of).collect(Collectors.toList());
    }
}
