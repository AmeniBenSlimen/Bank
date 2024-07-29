package com.pfe.Bank.dto;

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
    private String dtype;

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

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

        if (score instanceof NUMBER) {
            NUMBER number = (NUMBER) score;
            dto.setDtype("NUMBER");
            dto.setNum(number.getValeur());
            dto.setValeur(number.getValeur());
            System.out.println("NUMBER Score - valeur: " + number.getValeur());
        } else if (score instanceof ENUMERATION) {
            ENUMERATION enumScore = (ENUMERATION) score;
            dto.setDtype("ENUMERATION");
            dto.setEnumeration(enumScore.getValeur());
            dto.setValeur(enumScore.getValeur());
            System.out.println("ENUMERATION Score - valeur: " + enumScore.getValeur());
        } else if (score instanceof INTERVALE) {
            INTERVALE intervalScore = (INTERVALE) score;
            dto.setDtype("INTERVALE");
            dto.setVmin(intervalScore.getvMin());
            dto.setVmax(intervalScore.getvMax());
            dto.setValeur(Map.of("min", intervalScore.getvMin(), "max", intervalScore.getvMax()));
            System.out.println("INTERVALE Score - vMin: " + intervalScore.getvMin() + ", vMax: " + intervalScore.getvMax());
        } else if (score instanceof DATE) {
            DATE dateScore = (DATE) score;
            dto.setDtype("DATE");
            dto.setDate(dateScore.getValeur());
            dto.setValeur(dateScore.getValeur());
            System.out.println("DATE Score - valeur: " + dateScore.getValeur());
        }

        return dto;
    }




    public Score convertToEntity(ScoreDto dto) {
        Score score = null;

        switch (dto.getType()) {
            case "NUMBER":
                NUMBER NUMBER = new NUMBER();
                NUMBER.setId(dto.getId());
                NUMBER.setScore(dto.getScore());
                NUMBER.setValeur(dto.getNum());
                score = NUMBER;
                break;
            case "ENUMERATION":
                ENUMERATION ENUMERATION = new ENUMERATION();
                ENUMERATION.setId(dto.getId());
                ENUMERATION.setScore(dto.getScore());
                ENUMERATION.setValeur(dto.getEnumeration());
                score = ENUMERATION;
                break;
            case "INTERVALE":
                INTERVALE INTERVALE = new INTERVALE();
                INTERVALE.setId(dto.getId());
                INTERVALE.setScore(dto.getScore());
                INTERVALE.setvMin(dto.getVmin());
                INTERVALE.setvMax(dto.getVmax());
                score = INTERVALE;
                break;
            case "DATE":
                DATE DATE = new DATE();
                DATE.setId(dto.getId());
                DATE.setScore(dto.getScore());
                DATE.setValeur(dto.getDate());
                score = DATE;
                break;
        }

        return score;
    }
    public ScoreDto(DATE DATE) {
        this.id = DATE.getId();
        this.dtype = "DATE";
        this.valeur = DATE.getValeur();
    }

    public ScoreDto(ENUMERATION vEnum) {
        this.id = vEnum.getId();
        this.dtype = "ENUMERATION";
        this.valeur = vEnum.getValeur();
    }

    public ScoreDto(INTERVALE INTERVALE) {
        this.id = INTERVALE.getId();
        this.dtype = "INTERVALE";
        this.valeur = Map.of("min", INTERVALE.getvMin(), "max", INTERVALE.getvMax());
    }

    public ScoreDto(NUMBER vNumber) {
        this.id = vNumber.getId();
        this.dtype = "NUMBER";
        this.valeur = vNumber.getValeur();
    }

}