package com.pfe.Bank.model;

import java.util.List;

public class NotationQuest {
    private long id;
    private ResponseStatus status;
    private double note;

    public NotationQuest() {
    }

    public NotationQuest(long id, ResponseStatus status, double note, List<ResponseQuest> responses) {
        this.id = id;
        this.status = status;
        this.note = note;
        this.responses = responses;
    }

    private List<ResponseQuest> responses;

    public long getId() {
        return id;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public double getNote() {
        return note;
    }

    public List<ResponseQuest> getResponses() {
        return responses;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setResponses(List<ResponseQuest> responses) {
        this.responses = responses;
    }
}
