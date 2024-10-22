package com.pfe.Bank.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column (name="code_relation")
    private long codeRelation;
    @Column (name="id_nat")
    private String idNat;
    private String codeRelationFlexcube;
    private String identifiantProspect;
    private  String nom;
    private  String profession;
    private String adresse;
    private String agence;
    private String ville;
    private String region;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebutRelation;
    @Column(name="autre_information",columnDefinition="BLOB")
    @Lob
    private String autre;
    private LocalDateTime dateCreate;
    private  LocalDateTime dateUpdate;
    private boolean isfull;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Notation> notations;

    public List<Notation> getNotations() {
        return notations;
    }

    public void setNotations(List<Notation> notations) {
        this.notations = notations;
    }

    public Client(long id, long codeRelation, String idNat, String codeRelationFlexcube, String identifiantProspect, String nom, String profession, String adresse, String agence, String ville, String region, Date dateNaissance, Date dateDebutRelation, String autre, LocalDateTime dateCreate, LocalDateTime dateUpdate, boolean isfull) {
        super();
        this.id = id;
        this.codeRelation = codeRelation;
        this.idNat = idNat;
        this.codeRelationFlexcube = codeRelationFlexcube;
        this.identifiantProspect = identifiantProspect;
        this.nom = nom;
        this.profession = profession;
        this.adresse = adresse;
        this.agence = agence;
        this.ville = ville;
        this.region = region;
        this.dateNaissance = dateNaissance;
        this.dateDebutRelation = dateDebutRelation;
        this.autre = autre;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.isfull = isfull;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
