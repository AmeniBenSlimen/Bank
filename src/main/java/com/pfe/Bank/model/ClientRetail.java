package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SituationClientRetail> situations;
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
