package com.pfe.Bank.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DATE extends Score{
    @Temporal(TemporalType.DATE)
    private Date valeur;

    public Date getValeur() {
        return valeur;
    }

    public void setValeur(Date valeur) {
        this.valeur = valeur;
    }
}
