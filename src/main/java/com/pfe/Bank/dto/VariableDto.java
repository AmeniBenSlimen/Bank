package com.pfe.Bank.dto;

import com.pfe.Bank.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableDto {
    private String code;
    private String description;
    private Double coefficient;
    @Enumerated(EnumType.STRING)
    private Type type;
    private long modelid;
}
