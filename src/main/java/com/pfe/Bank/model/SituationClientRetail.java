package com.pfe.Bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/*ki nekteb code relation tatla3li situation ta3 l client hetheek hetha f recherche situation id_client ou code relation client la recherche importation w consultation w recherche ctt*/
@Entity
@Table(name = "rt_client_situation")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SituationClientRetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private ClientRetail client;
    private Long codeRelation;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeSituation;
    private String numeroComptePrincipal;
    private double mntEnConsolidation;
    private double encoursCT;
    private double encoursMT;
    private double encoursCreditTresorerie;
    private double ratioEngagementCDR;
    private String consolidationAutresBanques;
    private String besoinAccompagnement;
    private String besoinFinancement;
    private double rationEndettement;
    private String classeBanqueCentrale;
    private String anneeClassificationCentrale;
    private String ratingActuelleLegacy;
    private String classeRisqueLegacy;
    private int scoreClientLegacy;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRatingLegacy;
    private String impaye;
    private double montantImpayes;
    private double ratioImpayesEngagements;
    private int ancienneteImpayes;
    private int codeMaterielite;
    private double mouvementsTotauxAnneeN;
    private double mouvementsTotauxAnneeN1;
    private double mouvementCreditieurAnneeN;
    private double mouvementCreditieurAnneeN1;
    private double mouvementDebiteurAnneeN;
    private double mouvementDebiteurAnneeN1;
    private double ratioCreditSoldeMoyen;
    private String regulariteEcheances;
    private double dernierSalaireYTD;
    private double soldeMoyenAnnuelAnneeN;
    private double soldeMoyenAnnuelAnneeN1;
    private double totalCreancesGLE;
    private double variationAnnuelleMvtCreditN;
    private double variationAnnuelleMvtCreditN1;
    private double variationMvtCredit;
    private double rationSoldeMoyenFC;
    private String iarCentralRisquesCDR;
    private double variationEngagementCDR;
    private double mntCreditTresorerieCDR;
    private double variationCreditTresoCDR;
    private String incident;
    private String modeleApplicable;
    private String autresInformation;
    private String commentaire;
    private String variableLibre1;
    private String variableLibre2;
    private String variableLibre3;
    private String variableLibre4;
    private String variableLibre5;
    private String variableLibre6;
    private String variableLibre7;


}