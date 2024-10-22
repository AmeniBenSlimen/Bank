package com.pfe.Bank.dto;

public class ResponseDto {
    private long id;
    private String responseText;
    private long variableId;
    private String response;
    private VariableDto variable;

    public VariableDto getVariable() {
        return variable;
    }

    public void setVariable(VariableDto variable) {
        this.variable = variable;
    }

    public long getVariableId() {
        return variableId;
    }

    public void setVariableId(long variableId) {
        this.variableId = variableId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
