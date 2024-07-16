package com.pfe.Bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfe.Bank.model.Type;
import com.pfe.Bank.model.Variable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableDto {
    private long id;
    private String code;
    private String description;
    private Double coefficient;
    private Type type;
    private Long modelId;
    private List<ScoreDto> scores;


}
