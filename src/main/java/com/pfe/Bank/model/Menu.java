package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="hab_menu")
public class Menu {
    @Id
    @Column(name="cod_menu")
    private String codmenu;


    @Column (name="lib_menu")
    private String libmenu;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "codmod",referencedColumnName = "cod_module")
    @JsonIgnore
    private Modul module;
}
