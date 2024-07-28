package com.pfe.Bank.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pfe.Bank.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ScoreDto {
    private Long id;
    private Long variableId;
    private Double score;
    private String type;
    private Object valeur;
    private Double num;
    private String enumeration;
    private String vmin;
    private String vmax;
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(String enumeration) {
        this.enumeration = enumeration;
    }

    public String getVmin() {
        return vmin;
    }

    public void setVmin(String vmin) {
        this.vmin = vmin;
    }

    public String getVmax() {
        return vmax;
    }

    public void setVmax(String vmax) {
        this.vmax = vmax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ScoreDto convertToDto(Score score) {
        ScoreDto dto = new ScoreDto();
        dto.setId(score.getId());
        dto.setScore(score.getScore());

        if (score instanceof SVNumber) {
            SVNumber svNumber = (SVNumber) score;
           // dto.setDtype("SVNumber");
            dto.setNum(svNumber.getValeur());
        } else if (score instanceof SVEnum) {
            SVEnum svEnum = (SVEnum) score;
            //dto.setDtype("SVEnum");
            dto.setEnumeration(svEnum.getValeur());
        } else if (score instanceof SVInterval) {
            SVInterval svInterval = (SVInterval) score;
            //dto.setDtype("SVInterval");
            dto.setVmin(svInterval.getvMin());
            dto.setVmax(svInterval.getvMax());
        } else if (score instanceof SVDate) {
            SVDate svDate = (SVDate) score;
            //dto.setDtype("SVDate");
            dto.setDate(svDate.getValeur());
        }

        return dto;
    }

    public Score convertToEntity(ScoreDto dto) {
        Score score = null;

        switch (dto.getType()) {
            case "SVNumber":
                SVNumber svNumber = new SVNumber();
                svNumber.setId(dto.getId());
                svNumber.setScore(dto.getScore());
                svNumber.setValeur(dto.getNum());
                score = svNumber;
                break;
            case "SVEnum":
                SVEnum svEnum = new SVEnum();
                svEnum.setId(dto.getId());
                svEnum.setScore(dto.getScore());
                svEnum.setValeur(dto.getEnumeration());
                score = svEnum;
                break;
            case "SVInterval":
                SVInterval svInterval = new SVInterval();
                svInterval.setId(dto.getId());
                svInterval.setScore(dto.getScore());
                svInterval.setvMin(dto.getVmin());
                svInterval.setvMax(dto.getVmax());
                score = svInterval;
                break;
            case "SVDate":
                SVDate svDate = new SVDate();
                svDate.setId(dto.getId());
                svDate.setScore(dto.getScore());
                svDate.setValeur(dto.getDate());
                score = svDate;
                break;
        }

        return score;
    }
    public ScoreDto(SVDate svDate) {
        this.id = svDate.getId();
        //this.dtype = "DATE";
        this.valeur = svDate.getValeur();
    }

    public ScoreDto(SVEnum vEnum) {
        this.id = vEnum.getId();
       // this.dtype = "ENUMERATION";
        this.valeur = vEnum.getValeur();
    }

    public ScoreDto(SVInterval svInterval) {
        this.id = svInterval.getId();
        //this.dtype = "INTERVALE";
        this.valeur = Map.of("min", svInterval.getvMin(), "max", svInterval.getvMax());
    }

    public ScoreDto(SVNumber vNumber) {
        this.id = vNumber.getId();
        //this.dtype = "NUMBER";
        this.valeur = vNumber.getValeur();
    }

}
