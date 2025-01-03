package com.pfe.Bank.dto;

import com.pfe.Bank.model.SituationClientRetail;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SituationClientRetailDTO {
    private Long id;
    private Long clientId;
    private Date dateDeSituation;
    private String idNat;
    private Date dateDebutRelation;
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
    private long codeRelation;
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


    public SituationClientRetailDTO(SituationClientRetail situation){
        this.id = situation.getId();
        this.idNat = situation.getClient().getIdNat();
        this.clientId = situation.getClient().getId();
        this.dateDebutRelation = situation.getClient().getDateDebutRelation();
        this.codeRelation= situation.getCodeRelation();
        this.dateDeSituation = situation.getDateDeSituation();
        this.numeroComptePrincipal = situation.getNumeroComptePrincipal();
        this.mntEnConsolidation = situation.getMntEnConsolidation();
        this.encoursCT = situation.getEncoursCT();
        this.encoursMT = situation.getEncoursMT();
        this.encoursCreditTresorerie = situation.getEncoursCreditTresorerie();
        this.ratioEngagementCDR = situation.getRatioEngagementCDR();
        this.consolidationAutresBanques = situation.getConsolidationAutresBanques();
        this.besoinAccompagnement = situation.getBesoinAccompagnement();
        this.besoinFinancement = situation.getBesoinFinancement();
        this.rationEndettement = situation.getRationEndettement();
        this.classeBanqueCentrale = situation.getClasseBanqueCentrale();
        this.anneeClassificationCentrale = situation.getAnneeClassificationCentrale();
        this.ratingActuelleLegacy = situation.getRatingActuelleLegacy();
        this.classeRisqueLegacy = situation.getClasseRisqueLegacy();
        this.scoreClientLegacy = situation.getScoreClientLegacy();
        this.dateRatingLegacy = situation.getDateRatingLegacy();
        this.impaye = situation.getImpaye();
        this.montantImpayes = situation.getMontantImpayes();
        this.ratioImpayesEngagements = situation.getRatioImpayesEngagements();
        this.ancienneteImpayes = situation.getAncienneteImpayes();
        this.codeMaterielite = situation.getCodeMaterielite();
        this.mouvementsTotauxAnneeN = situation.getMouvementsTotauxAnneeN();
        this.mouvementsTotauxAnneeN1 = situation.getMouvementsTotauxAnneeN1();
        this.mouvementCreditieurAnneeN = situation.getMouvementCreditieurAnneeN();
        this.mouvementCreditieurAnneeN1 = situation.getMouvementCreditieurAnneeN1();
        this.mouvementDebiteurAnneeN = situation.getMouvementDebiteurAnneeN();
        this.mouvementDebiteurAnneeN1 = situation.getMouvementDebiteurAnneeN1();
        this.ratioCreditSoldeMoyen = situation.getRatioCreditSoldeMoyen();
        this.regulariteEcheances = situation.getRegulariteEcheances();
        this.dernierSalaireYTD = situation.getDernierSalaireYTD();
        this.soldeMoyenAnnuelAnneeN = situation.getSoldeMoyenAnnuelAnneeN();
        this.soldeMoyenAnnuelAnneeN1 = situation.getSoldeMoyenAnnuelAnneeN1();
        this.totalCreancesGLE = situation.getTotalCreancesGLE();
        this.variationAnnuelleMvtCreditN = situation.getVariationAnnuelleMvtCreditN();
        this.variationAnnuelleMvtCreditN1 = situation.getVariationAnnuelleMvtCreditN1();
        this.variationMvtCredit = situation.getVariationMvtCredit();
        this.rationSoldeMoyenFC = situation.getRationSoldeMoyenFC();
        this.iarCentralRisquesCDR = situation.getIarCentralRisquesCDR();
        this.variationEngagementCDR = situation.getVariationEngagementCDR();
        this.mntCreditTresorerieCDR = situation.getMntCreditTresorerieCDR();
        this.variationCreditTresoCDR = situation.getVariationCreditTresoCDR();
        this.incident = situation.getIncident();
        this.modeleApplicable = situation.getModeleApplicable();
        this.autresInformation = situation.getAutresInformation();
        this.commentaire = situation.getCommentaire();
        this.variableLibre1 = situation.getVariableLibre1();
        this.variableLibre2 = situation.getVariableLibre2();
        this.variableLibre3 = situation.getVariableLibre3();
        this.variableLibre4 = situation.getVariableLibre4();
        this.variableLibre5 = situation.getVariableLibre5();
        this.variableLibre6 = situation.getVariableLibre6();
        this.variableLibre7 = situation.getVariableLibre7();
    }

    public SituationClientRetailDTO(Long id, Long codeRelation, String numeroComptePrincipal,
                                    double rationEndettement, String classeRisqueLegacy, int scoreClientLegacy) {
        this.id = id;
        this.codeRelation = codeRelation;
        this.numeroComptePrincipal = numeroComptePrincipal;
        this.rationEndettement = rationEndettement;
        this.classeRisqueLegacy = classeRisqueLegacy;
        this.scoreClientLegacy = scoreClientLegacy;
    }
    public double calculateTotalDebtRatio() {
        return (encoursCT + encoursMT + encoursCreditTresorerie) / mntEnConsolidation;
    }
    public static SituationClientRetailDTO of(SituationClientRetail situationClientRetail){
        return new SituationClientRetailDTO(situationClientRetail);
    }

    public static List<SituationClientRetailDTO> of(List<SituationClientRetail> situations){
        return situations.stream().map(SituationClientRetailDTO::of).collect(Collectors.toList());
    }
}
