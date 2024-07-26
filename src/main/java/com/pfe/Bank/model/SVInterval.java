package com.pfe.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SVInterval extends Score {

    @Column(name = "vmin")
    private String vMin;

    @Column(name = "vmax")
    private String vMax;

    public String getvMin() {
        return vMin;
    }

    public void setvMin(String vMin) {
        this.vMin = vMin;
    }

    public String getvMax() {
        return vMax;
    }

    public void setvMax(String vMax) {
        this.vMax = vMax;
    }
}

