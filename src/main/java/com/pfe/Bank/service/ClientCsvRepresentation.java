package com.pfe.Bank.service;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCsvRepresentation {
    @CsvBindByName(column="code_relation_atlas")
    private long code_relation;
    @CsvBindByName(column = "identifiant_national")
    private String idNat;
    @CsvBindByName(column = "nomRelation")
    private String nom_relation;
    @CsvBindByName(column = "adresse")
    private String adr;
    @CsvBindByName(column = "agence")
    private String agence;
    @CsvBindByName(column = "ville")
    private String ville;
    @CsvBindByName(column = "region")
    private String region;
    @CsvBindByName(column = "nationalite")
    private String nationalite;
    @CsvBindByName(column = "date_naissance")
    private String dateNais;
    @CsvBindByName(column = "profession")
    private String prof;
    @CsvBindByName(column = "situation_familiale")
    private String situationFamiliale;
    @CsvBindByName(column = "date_debut_relation")
    private String date_debut_relation;
    @CsvBindByName(column = "dernier_salaire_domicile")
    private double salaireDomicile;
    @CsvBindByName(column = "autre_information")
    private String autre_info;
    @CsvBindByName(column = "date_embauche")
    private String date_embauche;
    @CsvBindByName(column = "code_relation_flexcube")
    private String code_relation_flexcube;
    @CsvBindByName(column = "identifiant_prospect")
    private String identifiant_prospect;
    @CsvBindByName(column = "New_PROFESSION_CODE")
    private long  New_PROFESSION_CODE;
    @CsvBindByName(column = "New_modle_UBCI")
    private String New_modle_UBCI;

}
