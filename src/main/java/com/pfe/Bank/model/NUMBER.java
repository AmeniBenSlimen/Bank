package com.pfe.Bank.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;



@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NUMBER extends Score{
    private Double valeur;

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }
}
