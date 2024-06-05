package com.pfe.Bank.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Client;
import com.pfe.Bank.model.ClientRetail;
import com.pfe.Bank.model.SituationClientRetail;
import com.pfe.Bank.repository.ClientRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class ClientService {
    @Autowired
    ClientRepository clientRepository;


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
                            .dateEmbauche(dateEmbauche)
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
    public List<Client> getClients(){
        return clientRepository.findAll();
    }
    public Client getClientById(long id) throws MissingEntity {
        Optional<Client> optional = clientRepository.findById(id);
        if (!optional.isPresent()) {
            throw new MissingEntity("client not found with id: " + id);
        }
        return optional.get();
    }
    public Optional<Client> findClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }
    public Client updateClient(long id, ClientRetail clientDetails) throws MissingEntity {
        Optional<Client> existingClientOptional = clientRepository.findById(id);
        if (!existingClientOptional.isPresent()) {
            throw new MissingEntity("Client not found with id: " + id);
        }

        Client existingClient = existingClientOptional.get();

        if (existingClient instanceof ClientRetail) {
            ClientRetail existingClientRetail = (ClientRetail) existingClient;
            existingClientRetail.setId(clientDetails.getId());
            existingClientRetail.setDateEmbauche(clientDetails.getDateEmbauche());
            existingClientRetail.setAdresse(clientDetails.getAdresse());
            existingClientRetail.setAgence(clientDetails.getAgence());
            existingClientRetail.setAutre(clientDetails.getAutre());
            existingClientRetail.setNom(clientDetails.getNom());
            existingClientRetail.setCodeRelation(clientDetails.getCodeRelation());
            existingClientRetail.setCodeRelationFlexcube(clientDetails.getCodeRelationFlexcube());
            existingClientRetail.setDateDebutRelation(clientDetails.getDateDebutRelation());
            existingClientRetail.setDateNaissance(clientDetails.getDateNaissance());
            existingClientRetail.setIdNat(clientDetails.getIdNat());
            existingClientRetail.setIdentifiantProspect(clientDetails.getIdentifiantProspect());
            existingClientRetail.setProfession(clientDetails.getProfession());
            existingClientRetail.setRegion(clientDetails.getRegion());
            existingClientRetail.setVille(clientDetails.getVille());

            log.info("ClientRetail mis à jour : " + existingClientRetail);
            return clientRepository.save(existingClientRetail);
        } else {
            throw new MissingEntity("Client is not an instance of ClientRetail with id: " + id);
        }
    }
    @Transactional
    public Map<String, Boolean> deleteClient(long id) throws MissingEntity {
        Client client = getClientById(id);
        clientRepository.delete(client);
        Map<String,Boolean> map = new HashMap<>();
        map.put(" client deleted",Boolean.TRUE);
        return map;
    }
    public List<Client> findByCodeRelation(long codeRelation) {
        return clientRepository.findByCodeRelation(codeRelation);
    }
}