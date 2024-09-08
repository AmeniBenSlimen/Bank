package com.pfe.Bank.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class NotationDto {
    private long id;
    private ResponseStatus status;
    private double note;

    @OneToMany(mappedBy = "notation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Response> responses;

    public long getId() {
        return id;
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

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public double getNote() {
        return note;
    }

    public List<Response> getResponses() {
        return responses;
    }
}
