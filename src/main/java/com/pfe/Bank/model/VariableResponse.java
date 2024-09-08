package com.pfe.Bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Getter
@Setter
@NoArgsConstructor
public class VariableResponse {
    private long id;
    private String code;
    private String description;
    private Double coefficient;
    private Object response;
    @Enumerated(EnumType.STRING)
    private Type type;

    public VariableResponse(long id, String code, String description, Double coefficient, Object response, Type type) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.coefficient = coefficient;
        this.response = response;
        this.type = type;
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
}
