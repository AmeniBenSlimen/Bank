package com.pfe.Bank.dto;

import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private long id;
    private double score;
    private String valeur;
    private Long variableId;

}
