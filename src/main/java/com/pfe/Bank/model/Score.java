package com.pfe.Bank.model;

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
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Convert(converter = JpaConverterJson.class)
    private Object valeur;
    private double score;
    @ManyToOne
    @JoinColumn(name = "variable_id", nullable = false)
    private Variable variable;
}
