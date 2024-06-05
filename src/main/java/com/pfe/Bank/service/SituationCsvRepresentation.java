package com.pfe.Bank.service;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituationCsvRepresentation {
    @CsvBindByName(column="code_relation")
    private long codeRelation;

    @CsvBindByName(column="date_de_situation")
    private String dateDeSituation;

    @CsvBindByName(column="numero_compte_principal")
    private String numeroComptePrincipal;

    @CsvBindByName(column="mnt_en_consolidation")
    private double mntEnConsolidation;

    @CsvBindByName(column="encours_ct")
    private double encours_ct;

    @CsvBindByName(column="encours_mt")

    private double encoursMT;
    @CsvBindByName(column="encours_credit_tresorerie")

    private double encoursCreditTresorerie;
    @CsvBindByName(column="ratio_engagement_cdr")

    private double ratioEngagementCDR;
    @CsvBindByName(column="consolidation_autres_banques")

    private String consolidationAutresBanques;
    @CsvBindByName(column="besoin_accompagnement")

    private String besoinAccompagnement;
    @CsvBindByName(column="besoin_financement")

    private String besoinFinancement;
    @CsvBindByName(column="ratio_endettement")

    private double rationEndettement;
    @CsvBindByName(column="classe_banque_centrale")

    private String classeBanqueCentrale;
    @CsvBindByName(column="annee_classification_centrale")

    private String anneeClassificationCentrale;
    @CsvBindByName(column="rating_actuel_legacy")

    private String ratingActuelleLegacy;
    @CsvBindByName(column="classe_risque_legacy")

    private String classeRisqueLegacy;
    @CsvBindByName(column="score_client_legacy")

    private int scoreClientLegacy;

    @CsvBindByName(column="date_rating_legacy")
    private String dateRatingLegacy;

    @CsvBindByName(column="impaye")

    private String impaye;
    @CsvBindByName(column="montant_impayes")

    private double montantImpayes;
    @CsvBindByName(column="ratio_impayes_engagements")

    private double ratioImpayesEngagements;
    @CsvBindByName(column="anciennete_impayes")

    private int ancienneteImpayes;
    @CsvBindByName(column="code_materialite")

    private int codeMaterielite;
    @CsvBindByName(column="mouvements_totaux_year_n")

    private double mouvementsTotauxAnneeN;
    @CsvBindByName(column="mouvements_totaux_year_n1")

    private double mouvementsTotauxAnneeN1;
    @CsvBindByName(column="mouvement_crediteur_year_n")

    private double mouvementCreditieurAnneeN;
    @CsvBindByName(column="mouvement_crediteur_year_n1")

    private double mouvementCreditieurAnneeN1;
    @CsvBindByName(column="mouvement_debiteur_year_n")

    private double mouvementDebiteurAnneeN;
    @CsvBindByName(column="mouvement_debiteur_year_n1")

    private double mouvementDebiteurAnneeN1;
    @CsvBindByName(column="ratio_credit_solde_moyen")

    private double ratioCreditSoldeMoyen;
    @CsvBindByName(column="regularite_echeances")

    private String regulariteEcheances;
    @CsvBindByName(column="dernier_salaire_ytd")

    private double dernierSalaireYTD;
    @CsvBindByName(column="solde_moyen_annuel_year_n")

    private double soldeMoyenAnnuelAnneeN;
    @CsvBindByName(column="solde_moyen_annuel_year_n1")

    private double soldeMoyenAnnuelAnneeN1;
    @CsvBindByName(column="total_creances_gle")

    private double totalCreancesGLE;
    @CsvBindByName(column="variation_annual_mouvement_crediteur_n")

    private double variationAnnuelleMvtCreditN;
    @CsvBindByName(column="variation_annual_mouvement_crediteur_n1")

    private double variationAnnuelleMvtCreditN1;
    @CsvBindByName(column="variation_mvt_crediteur")

    private double variationMvtCredit;
    @CsvBindByName(column="ratio_solde_moyen_fc")

    private double rationSoldeMoyenFC;
    @CsvBindByName(column="iar_cdr")

    private String iarCentralRisquesCDR;
    @CsvBindByName(column="variation_engagement_cdr")

    private double variationEngagementCDR;
    @CsvBindByName(column="mnt_credit_treso_cdr")

    private double mntCreditTresorerieCDR;
    @CsvBindByName(column="variation_credit_treso_cdr")

    private double variationCreditTresoCDR;
    @CsvBindByName(column="incident")

    private String incident;
    @CsvBindByName(column="modele_applicable")

    private String modeleApplicable;
    @CsvBindByName(column="autres_information")

    private String autresInformation;
    @CsvBindByName(column="commentaire")

    private String commentaire;
    @CsvBindByName(column="variable_libre_1")

    private String variableLibre1;
    @CsvBindByName(column="variable_libre_2")

    private String variableLibre2;
    @CsvBindByName(column="variable_libre_3")

    private String variableLibre3;
    @CsvBindByName(column="variable_libre_4")

    private String variableLibre4;
    @CsvBindByName(column="variable_libre_5")

    private String variableLibre5;
    @CsvBindByName(column="variable_libre_6")

    private String variableLibre6;
    @CsvBindByName(column="variable_libre_7")

    private String variableLibre7;

}
