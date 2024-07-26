package com.pfe.Bank.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SVEnum extends Score{
    private String valeur;

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
