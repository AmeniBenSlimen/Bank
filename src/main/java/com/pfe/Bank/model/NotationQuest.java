package com.pfe.Bank.model;

import java.util.List;

public class NotationQuest {
    private long id;
    private ResponseStatus status;
    private double note;
    private long clientId;
    private String nom;
    public NotationQuest() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public NotationQuest(long id, ResponseStatus status, double note, List<ResponseQuest> responses, long clientId, String nom) {
        this.id = id;
        this.status = status;
        this.note = note;
        this.responses = responses;
        this.clientId = clientId;
        this.nom = nom;
    }

    private List<ResponseQuest> responses;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

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
