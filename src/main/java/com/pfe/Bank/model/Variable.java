package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="variable",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code")
}
)
public class Variable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String description;
    private Double coefficient;
    @Enumerated(EnumType.STRING)
    private Type type;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "modele_id",referencedColumnName = "id")
    private Modele modele;

}
