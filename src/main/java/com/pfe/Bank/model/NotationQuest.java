package com.pfe.Bank.model;

import java.util.Date;
import java.util.List;

public class NotationQuest {
    private long id;
    private ResponseStatus status;
    private double note;
    private long clientId;
    private Date createdDate;
    private String nom;
    private List<ResponseQuest> responses;

    // Le constructeur initialisé
    public NotationQuest(long id, ResponseStatus status, double note, List<ResponseQuest> responses, long clientId, String nom, Date createdDate) {
        this.id = id;
        this.status = status;
        this.note = note;
        this.responses = responses;
        this.clientId = clientId;
        this.nom = nom;
        this.createdDate = createdDate;
    }

    // Getters et Setters
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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
