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
    private Responses responseMeaning;
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

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

    public Responses getResponseMeaning() {
        return responseMeaning;
    }

    public void setResponseMeaning(Responses responseMeaning) {
        this.responseMeaning = responseMeaning;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public List<ScoreDto> getScores() {
        return scores;
    }

    public void setScores(List<ScoreDto> scores) {
        this.scores = scores;
    }
}
