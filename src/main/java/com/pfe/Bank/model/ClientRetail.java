package com.pfe.Bank.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@DiscriminatorValue("retail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ClientRetail extends Client {
    private String nationalite;
    private String situationFamiliale;
    private double salaireDomicile;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEmbauche;

    private long newProfessionCode;

    private String newModelUBCI;
    @Builder
    public ClientRetail (long codeRelation, String idNat, String codeRelationFlexcube, String identifiantProspect, String nom, String profession, String adresse, String agence, String ville, String region, Date dateNaissance, Date dateDebutRelation, String autre, LocalDateTime dateCreate, LocalDateTime dateUpdate,boolean isfull, long id, String nationalite, String situationFamiliale, double salaireDomicile, Date dateEmbauche, long newProfessionCode, String newModelUBCI){
        super();
        this.dateEmbauche = dateEmbauche;
        this.nationalite = nationalite;
        this.salaireDomicile = salaireDomicile;
        this.newModelUBCI = newModelUBCI;
        this.newProfessionCode = newProfessionCode;
        this.situationFamiliale = situationFamiliale;
        super.setCodeRelation(codeRelation);
        super.setIdNat(idNat);
        super.setCodeRelationFlexcube(codeRelationFlexcube);
        super.setIdentifiantProspect(identifiantProspect);
        super.setNom(nom);
        super.setProfession(profession);
        super.setAdresse(adresse);
        super.setAgence(agence);
        super.setVille(ville);
        super.setDateNaissance(dateNaissance);
        super.setDateDebutRelation(dateDebutRelation);
        super.setAutre(autre);
        super.setDateCreate(dateCreate);
        super.setDateUpdate(dateUpdate);
        super.setIsfull(isfull);
        super.setRegion(region);
        super.setId(id);
    }
}
