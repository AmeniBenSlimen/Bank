package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private ResponseStatus status;
    private double note;
    @Enumerated(EnumType.STRING)
    private Appreciation appreciation;
    @OneToMany(mappedBy = "notation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<com.pfe.Bank.model.Response> responses;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @Temporal(TemporalType.TIMESTAMP)  // Vous pouvez utiliser DATE si vous ne voulez que la date sans l'heure
    private Date createdDate = new Date();
    public Appreciation getAppreciation() {
        return appreciation;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setAppreciation(Appreciation appreciation) {
        this.appreciation = appreciation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

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

    public void setResponses(List<com.pfe.Bank.model.Response> responses) {
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


    public int getProgressPercentage() {
        if (status == ResponseStatus.IN_PROGRESS) {
            return 0;
        }
        else if (status == ResponseStatus.DONE) {
            return 100;
        }
        return -1;
    }
}
