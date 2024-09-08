package com.pfe.Bank.model;

public class ResponseQuest {
    private long id;
    private long variableId;
    private String response;
    private String description;

    public ResponseQuest(long id, long variableId, String response,String description) {
        this.id = id;
        this.variableId = variableId;
        this.response = response;
        this.description= description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponseQuest() {
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
