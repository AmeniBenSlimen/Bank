package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="hab_privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_role",referencedColumnName = "id")
    private Role role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_menu",referencedColumnName = "cod_menu")
    private Menu menu;

}
