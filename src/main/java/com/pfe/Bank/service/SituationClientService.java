package com.pfe.Bank.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Client;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private ClientRetail getClientById(Long clientId) throws MissingEntity {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new MissingEntity("Client not found with id: " + clientId));
    }
    public Set<SituationClientRetail> uploadSituations(MultipartFile file) throws IOException {
        Set<SituationClientRetail> situations = parseCsv(file);
        repository.saveAll(situations);
        return situations;
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
    public List<SituationClientRetail> getSituations(){
        return situationClientRepository.findAll();
    }
}

