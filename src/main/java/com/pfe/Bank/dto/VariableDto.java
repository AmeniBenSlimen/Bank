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
                    if (score instanceof SVNumber) {
                        scoreDto.setType("SVNumber");
                        scoreDto.setNum(((SVNumber) score).getValeur());
                    } else if (score instanceof SVEnum) {
                        scoreDto.setType("SVEnum");
                        scoreDto.setEnumeration(((SVEnum) score).getValeur());
                    } else if (score instanceof SVInterval) {
                        scoreDto.setType("SVInterval");
                        scoreDto.setVmin(((SVInterval) score).getvMin());
                        scoreDto.setVmax(((SVInterval) score).getvMax());
                    } else if (score instanceof SVDate) {
                        scoreDto.setType("SVDate");
                        scoreDto.setDate(((SVDate) score).getValeur());
                    }
                    return scoreDto;
                })
                .collect(Collectors.toList()));

        return dto;
    }
}
