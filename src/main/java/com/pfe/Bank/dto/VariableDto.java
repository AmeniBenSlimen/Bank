package com.pfe.Bank.dto;

import com.pfe.Bank.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static VariableDto of(Variable variable) {
        VariableDto dto = new VariableDto();
        dto.setId(variable.getId());
        dto.setCode(variable.getCode());
        dto.setDescription(variable.getDescription());
        dto.setCoefficient(variable.getCoefficient());
        dto.setType(variable.getType());

        dto.setScores(variable.getScores().stream()
                .map(score -> {
                    ScoreDto scoreDto = new ScoreDto();
                    scoreDto.setId(score.getId());
                    scoreDto.setScore(score.getScore());
                    // Removed setValeur, use appropriate mapping based on Score type
                    if (score instanceof NUMBER) {
                        scoreDto.setType("NUMBER");
                        scoreDto.setNum(((NUMBER) score).getValeur());
                    } else if (score instanceof ENUMERATION) {
                        scoreDto.setType("ENUMERATION");
                        scoreDto.setEnumeration(((ENUMERATION) score).getValeur());
                    } else if (score instanceof INTERVALE) {
                        scoreDto.setType("INTERVALE");
                        scoreDto.setVmin(((INTERVALE) score).getvMin());
                        scoreDto.setVmax(((INTERVALE) score).getvMax());
                    } else if (score instanceof DATE) {
                        scoreDto.setType("DATE");
                        scoreDto.setDate(((DATE) score).getValeur());
                    }
                    return scoreDto;
                })
                .collect(Collectors.toList()));

        return dto;
    }
}
