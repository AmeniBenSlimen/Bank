package com.pfe.Bank.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
