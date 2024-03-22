package com.pfe.Bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import javax.persistence.OneToMany;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="hab_module")
public class Modul {
    @Id
    @Column(name = "cod_module")
    private String codmodule;


    @Column(name = "lib_module")
    private String libmodule;

    @OneToMany(mappedBy = "module")
    List<Menu> menus;
}
