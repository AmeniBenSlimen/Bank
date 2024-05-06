package com.pfe.Bank.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.pfe.Bank.model.Client;
import com.pfe.Bank.model.ClientRetail;
import com.pfe.Bank.repository.ClienRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class ClientService {
    @Autowired
    ClienRepository clientRepository;
    @Transactional
    public Set<Client> uploadClients(MultipartFile file) throws IOException {
        Set<ClientRetail> clients = parseCsv(file);
        Set<Client> updatedClients = new HashSet<>();

        for (ClientRetail client : clients) {
            Optional<Client> existingClientOptional = clientRepository.findByCodeRelationAndIdNat(client.getCodeRelation(), client.getIdNat());
            if (existingClientOptional.isPresent()) {
                Client existingClient = existingClientOptional.get();

                if (existingClient instanceof ClientRetail) {
                    ClientRetail existingClientRetail = (ClientRetail) existingClient;
                    existingClientRetail.setDateEmbauche(client.getDateEmbauche());
                    existingClientRetail.setAdresse(client.getAdresse());
                    existingClient.setAgence(client.getAgence());
                    existingClient.setAutre(client.getAutre());
                    existingClient.setNom(client.getNom());
                    existingClient.setCodeRelation(client.getCodeRelation());
                    existingClient.setCodeRelationFlexcube(client.getCodeRelationFlexcube());
                    existingClient.setDateDebutRelation(client.getDateDebutRelation());
                    existingClient.setDateNaissance(client.getDateNaissance());
                    existingClient.setIdNat(client.getIdNat());
                    existingClient.setIdentifiantProspect(client.getIdentifiantProspect());
                    existingClient.setProfession(client.getProfession());
                    existingClient.setRegion(client.getRegion());
                    existingClient.setVille(client.getVille());
                    clientRepository.save(existingClient);
                    updatedClients.add(existingClient);
                    log.info("ClientRetail mis à jour : " + existingClientRetail);
                }
            } else {
                clientRepository.save(client);
                updatedClients.add(client);
                log.info("Nouveau ClientRetail inséré : " + client);
            }
        }
        return updatedClients;
    }




    private static final Logger log = (Logger) LoggerFactory.getLogger(ClientService.class);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public Set<ClientRetail> parseCsv(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<ClientCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ClientCsvRepresentation.class);

            CsvToBean<ClientCsvRepresentation> csvToBean = new CsvToBeanBuilder<ClientCsvRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse().stream().map(csvLine -> {
                try {
                    log.info("Parsing date_embauche: " + csvLine.getDate_embauche());
                    Date dateEmbauche = simpleDateFormat.parse(csvLine.getDate_embauche());
                    log.info("Parsed date_embauche: " + dateEmbauche);

                    ClientRetail clientRetail = ClientRetail.builder()
                            .codeRelation(csvLine.getCode_relation())
                            .nom(csvLine.getNom_relation())
                            .idNat(csvLine.getIdNat())
                            .adresse(csvLine.getAdr())
                            .agence(csvLine.getAgence())
                            .ville(csvLine.getVille())
                            .region(csvLine.getRegion())
                            .nationalite(csvLine.getNationalite())
                            .dateNaissance(simpleDateFormat.parse(csvLine.getDateNais()))
                            .profession(csvLine.getProf())
                            .dateEmbauche(dateEmbauche) // Utilisation de la date d'embauche parsée
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
                    log.error("Erreur de parsing pour date_embauche: " + csvLine.getDate_embauche(), e);
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toSet());
        }
    }
}
