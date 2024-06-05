package com.pfe.Bank.form;

import com.pfe.Bank.model.Modele;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModeleForm {
    private long id;
    private String nom;
    private String description;
    private Date dateCreation;
    private Date updateDate;
    private boolean used;
    private boolean updatebale;
    private Date nextUpdateDate;
    private Date lastUsedDate;
    private boolean disabled;
    public ModeleForm(Modele modele){
        this.id=modele.getId();
        this.nom=modele.getName();
        this.description= modele.getDescription();
        this.dateCreation=modele.getDateCreation();
        this.updateDate=modele.getDateUpdate();
        this.used=modele.isUsed();
        this.updatebale= modele.isUpdatebale();
        this.nextUpdateDate=modele.getNextUpdateDate();
        this.lastUsedDate=modele.getLastUsedDate();
        this.disabled= modele.isDisabled();

    }
}
