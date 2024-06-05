package com.pfe.Bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="modele")
public class Modele {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdate;
    private boolean used;
    private boolean updatebale;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextUpdateDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastUsedDate;
    private boolean disabled;
    private boolean deleted = false;
    @PrePersist
    public void prePersist() {
        if (!updatebale) {
            nextUpdateDate = null;
        }
        if (!used) {
            lastUsedDate = null;
        }
        dateCreation = new Date();
        dateUpdate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        if (!updatebale) {
            nextUpdateDate = null;
        }
        if (!used) {
            lastUsedDate = null;
        }
        dateUpdate = new Date();
    }
}
