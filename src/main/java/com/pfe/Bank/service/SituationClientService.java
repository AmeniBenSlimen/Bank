package com.pfe.Bank.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.pfe.Bank.dto.SituationClientRetailDTO;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.ClientRetail;
import com.pfe.Bank.model.SituationClientRetail;
import com.pfe.Bank.repository.ClientRetailRepository;
import com.pfe.Bank.repository.SituationClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SituationClientService {
    @Autowired
    private  SituationClientRepository repository;

    @Autowired
    private ClientRetailRepository clientRepository;
    @Autowired
    private ClientService clientService;
    private static final Logger log = (Logger) LoggerFactory.getLogger(SituationClientService.class);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Transactional
    public Set<SituationClientRetail> uploadSituations(MultipartFile file) throws IOException {
        Set<SituationClientRetail> situations = new HashSet<>();
        Set<SituationClientRetail> updatedSituations = parseCsv(file);

        for (SituationClientRetail csvLine : updatedSituations) {
            Optional<SituationClientRetail> existingSituationOptional = repository.findByClientAndDateDeSituation(csvLine.getClient(), csvLine.getDateDeSituation());
            if (existingSituationOptional.isPresent()) {
                SituationClientRetail existingSituation = existingSituationOptional.get();
                existingSituation.setNumeroComptePrincipal(csvLine.getNumeroComptePrincipal());
                existingSituation.setMntEnConsolidation(csvLine.getMntEnConsolidation());
                existingSituation.setEncoursCT(csvLine.getEncoursCT());
                existingSituation.setEncoursMT(csvLine.getEncoursMT());
                existingSituation.setEncoursCreditTresorerie(csvLine.getEncoursCreditTresorerie());
                existingSituation.setRatioEngagementCDR(csvLine.getVariationEngagementCDR());
                existingSituation.setConsolidationAutresBanques(csvLine.getConsolidationAutresBanques());
                existingSituation.setBesoinAccompagnement(csvLine.getBesoinAccompagnement());
                existingSituation.setBesoinFinancement(csvLine.getBesoinFinancement());
                existingSituation.setRationEndettement(csvLine.getRationEndettement());
                existingSituation.setClasseBanqueCentrale(csvLine.getClasseBanqueCentrale());
                existingSituation.setAnneeClassificationCentrale(csvLine.getAnneeClassificationCentrale());
                existingSituation.setRatingActuelleLegacy(csvLine.getRatingActuelleLegacy());
                existingSituation.setClasseRisqueLegacy(csvLine.getClasseRisqueLegacy());
                existingSituation.setScoreClientLegacy(csvLine.getScoreClientLegacy());
                existingSituation.setDateRatingLegacy(csvLine.getDateRatingLegacy());
                existingSituation.setImpaye(csvLine.getImpaye());
                existingSituation.setMontantImpayes(csvLine.getMontantImpayes());
                existingSituation.setRatioImpayesEngagements(csvLine.getRatioImpayesEngagements());
                existingSituation.setAncienneteImpayes(csvLine.getAncienneteImpayes());
                existingSituation.setCodeMaterielite(csvLine.getCodeMaterielite());
                existingSituation.setMouvementsTotauxAnneeN(csvLine.getMouvementsTotauxAnneeN());
                existingSituation.setMouvementCreditieurAnneeN1(csvLine.getMouvementCreditieurAnneeN1());
                existingSituation.setMouvementCreditieurAnneeN(csvLine.getMouvementCreditieurAnneeN());
                existingSituation.setMouvementCreditieurAnneeN1(csvLine.getMouvementCreditieurAnneeN1());
                existingSituation.setMouvementDebiteurAnneeN(csvLine.getMouvementDebiteurAnneeN());
                existingSituation.setMouvementDebiteurAnneeN1(csvLine.getMouvementDebiteurAnneeN1());
                existingSituation.setRatioCreditSoldeMoyen(csvLine.getRatioCreditSoldeMoyen());
                existingSituation.setRegulariteEcheances(csvLine.getRegulariteEcheances());
                existingSituation.setDernierSalaireYTD(csvLine.getDernierSalaireYTD());
                existingSituation.setSoldeMoyenAnnuelAnneeN(csvLine.getSoldeMoyenAnnuelAnneeN());
                existingSituation.setSoldeMoyenAnnuelAnneeN1(csvLine.getSoldeMoyenAnnuelAnneeN1());
                existingSituation.setAutresInformation(csvLine.getAutresInformation());
                repository.save(existingSituation);
                situations.add(existingSituation);
                log.info("SituationClientRetail mise à jour : " + existingSituation);
            } else {
                clientRepository.save(csvLine.getClient());
                situations.add(csvLine);
                log.info("Nouveau ClientRetail inséré : " + csvLine);
            }
        }
        return situations;
    }


    public Optional<SituationClientRetail> findByClientAndDateDeSituation(ClientRetail client, Date dateDeSituation) {
        return repository.findByClientAndDateDeSituation(client, dateDeSituation);
    }
    private Set<SituationClientRetail> parseCsv(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<SituationCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SituationCsvRepresentation.class);
            CsvToBean<SituationCsvRepresentation> csvToBean = new CsvToBeanBuilder<SituationCsvRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> {
                        try {
                            Date dateDeSituation = simpleDateFormat.parse(csvLine.getDateDeSituation());
                            Date dateRatingLegacy = simpleDateFormat.parse(csvLine.getDateRatingLegacy());

                            Optional<ClientRetail> existingClientOptional = clientRepository.findByCodeRelation(csvLine.getCode_relation());
                            if (!existingClientOptional.isPresent()) {
                                throw new MissingEntity("Client not found with code relation: " + csvLine.getCode_relation());

                            }
                            ClientRetail existingClient = existingClientOptional.get();

                            return SituationClientRetail.builder()
                                    .client(existingClient)
                                    .dateDeSituation(dateDeSituation)
                                    .numeroComptePrincipal(csvLine.getNumeroComptePrincipal())
                                    .mntEnConsolidation(csvLine.getMntEnConsolidation())
                                    .encoursCT(csvLine.getEncours_ct())
                                    .encoursMT(csvLine.getEncoursMT())
                                    .encoursCreditTresorerie(csvLine.getEncoursCreditTresorerie())
                                    .ratioEngagementCDR(csvLine.getVariationEngagementCDR())
                                    .consolidationAutresBanques(csvLine.getConsolidationAutresBanques())
                                    .besoinAccompagnement(csvLine.getBesoinAccompagnement())
                                    .besoinFinancement(csvLine.getBesoinFinancement())
                                    .rationEndettement(csvLine.getRationEndettement())
                                    .classeBanqueCentrale(csvLine.getClasseBanqueCentrale())
                                    .anneeClassificationCentrale(csvLine.getAnneeClassificationCentrale())
                                    .ratingActuelleLegacy(csvLine.getRatingActuelleLegacy())
                                    .classeRisqueLegacy(csvLine.getClasseRisqueLegacy())
                                    .scoreClientLegacy(csvLine.getScoreClientLegacy())
                                    .dateRatingLegacy(dateRatingLegacy)
                                    .impaye(csvLine.getImpaye())
                                    .montantImpayes(csvLine.getMontantImpayes())
                                    .ratioImpayesEngagements(csvLine.getRatioImpayesEngagements())
                                    .ancienneteImpayes(csvLine.getAncienneteImpayes())
                                    .codeMaterielite(csvLine.getCodeMaterielite())
                                    .mouvementsTotauxAnneeN(csvLine.getMouvementsTotauxAnneeN())
                                    .mouvementCreditieurAnneeN1(csvLine.getMouvementsTotauxAnneeN1())
                                    .mouvementCreditieurAnneeN(csvLine.getMouvementCreditieurAnneeN())
                                    .mouvementCreditieurAnneeN1(csvLine.getMouvementCreditieurAnneeN1())
                                    .mouvementDebiteurAnneeN(csvLine.getMouvementDebiteurAnneeN())
                                    .mouvementDebiteurAnneeN1(csvLine.getMouvementDebiteurAnneeN1())
                                    .ratioCreditSoldeMoyen(csvLine.getRatioCreditSoldeMoyen())
                                    .regulariteEcheances(csvLine.getRegulariteEcheances())
                                    .dernierSalaireYTD(csvLine.getDernierSalaireYTD())
                                    .soldeMoyenAnnuelAnneeN(csvLine.getSoldeMoyenAnnuelAnneeN())
                                    .soldeMoyenAnnuelAnneeN1(csvLine.getSoldeMoyenAnnuelAnneeN1())
                                    .totalCreancesGLE(csvLine.getTotalCreancesGLE())
                                    .variationAnnuelleMvtCreditN(csvLine.getVariationAnnuelleMvtCreditN())
                                    .variationAnnuelleMvtCreditN1(csvLine.getVariationAnnuelleMvtCreditN1())
                                    .variationMvtCredit(csvLine.getVariationMvtCredit())
                                    .rationSoldeMoyenFC(csvLine.getRationSoldeMoyenFC())
                                    .iarCentralRisquesCDR(csvLine.getIarCentralRisquesCDR())
                                    .variationEngagementCDR(csvLine.getVariationEngagementCDR())
                                    .mntCreditTresorerieCDR(csvLine.getMntCreditTresorerieCDR())
                                    .variationCreditTresoCDR(csvLine.getVariationCreditTresoCDR())
                                    .incident(csvLine.getIncident())
                                    .modeleApplicable(csvLine.getModeleApplicable())
                                    .autresInformation(csvLine.getAutresInformation())
                                    .commentaire(csvLine.getCommentaire())
                                    .variableLibre1(csvLine.getVariableLibre1())
                                    .variableLibre2(csvLine.getVariableLibre2())
                                    .variableLibre3(csvLine.getVariableLibre3())
                                    .variableLibre4(csvLine.getVariableLibre4())
                                    .variableLibre5(csvLine.getVariableLibre5())
                                    .variableLibre6(csvLine.getVariableLibre6())
                                    .variableLibre7(csvLine.getVariableLibre7())
                                    .build();
                        } catch (ParseException e) {
                            log.error("Parsing error for date: {}", csvLine.getDateDeSituation(), e);
                            throw new RuntimeException(e);
                        } catch (MissingEntity e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toSet());
        }
    }
    @Autowired
    SituationClientRepository situationClientRepository;
    public List<SituationClientRetailDTO> getSituations() {
        return situationClientRepository.findAll().stream()
                .map(SituationClientRetailDTO::new)
                .collect(Collectors.toList());
    }
    public SituationClientRetail getSituationById(long id) throws MissingEntity {
        Optional<SituationClientRetail> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new MissingEntity("situation not found with id: " + id);
        }
        return optional.get();
    }
    public Optional<SituationClientRetail> findSituationById(Long id) {
        return repository.findById(id);
    }
}

