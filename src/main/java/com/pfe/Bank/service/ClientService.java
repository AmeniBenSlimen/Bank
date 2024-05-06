package com.pfe.Bank.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.pfe.Bank.model.Client;
import com.pfe.Bank.model.ClientRetail;
import com.pfe.Bank.repository.ClienRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {
    @Autowired
    ClienRepository clienRepository;
    public Set<Client> uploadClients(MultipartFile file) throws IOException {
        return parseCsv(file);
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Set<Client> parseCsv(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<ClientCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ClientCsvRepresentation.class);
            CsvToBean<ClientCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<ClientCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> {
                                try {
                                    ClientRetail clientRetail = ClientRetail.builder()
                                            .codeRelation(csvLine.getCode_relation())
                                            .idNat(csvLine.getIdNat())
                                            .nom(csvLine.getNom_relation())
                                            .adresse(csvLine.getAdr())
                                            .agence(csvLine.getAgence())
                                            .ville(csvLine.getVille())
                                            .region(csvLine.getRegion())
                                            .nationalite(csvLine.getNationalite())
                                            .dateNaissance(simpleDateFormat.parse(csvLine.getDateNais()))
                                            .profession(csvLine.getProf())
                                            .situationFamiliale(csvLine.getSituationFamiliale())
                                            .salaireDomicile(csvLine.getSalaireDomicile())
                                            .dateDebutRelation(simpleDateFormat.parse(csvLine.getDate_debut_relation()))
                                            .autre(csvLine.getAutre_info())
                                            .codeRelationFlexcube(csvLine.getCode_relation_flexcube())
                                            .identifiantProspect(csvLine.getIdentifiant_prospect())
                                            .newProfessionCode(csvLine.getNew_PROFESSION_CODE())
                                            .newModelUBCI(csvLine.getNew_modle_UBCI())
                                            .build();
                                         log.info(clientRetail.toString());
                                         return clientRetail;
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                    ).collect(Collectors.toSet());
        }
    }
}
