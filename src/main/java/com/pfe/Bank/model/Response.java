package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long variableId;
    private String response;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "variableId", referencedColumnName = "id", insertable = false, updatable = false)
    private Variable variable;


    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }
    public Response() {}
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Notation notation;

    public void setNotation(Notation notation) {
        this.notation = notation;
    }

    public Notation getNotation() {
        return notation;
    }

    public long getId() {
        return id;
    }

    public long getVariableId() {
        return variableId;
    }

    public String getResponse() {
        return response;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVariableId(long variableId) {
        this.variableId = variableId;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
