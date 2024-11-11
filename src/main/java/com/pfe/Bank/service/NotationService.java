package com.pfe.Bank.service;

import com.pfe.Bank.dto.ResponseDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ClientRepository;
import com.pfe.Bank.repository.NotationRepository;
import com.pfe.Bank.repository.ResponseRepository;
import com.pfe.Bank.repository.VariableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pfe.Bank.model.Type.*;
import static com.pfe.Bank.model.Type.DATE;

@RequiredArgsConstructor
@Service
public class NotationService {
    @Autowired
    private NotationRepository notationRepository;
    @Autowired
    private VariableRepository variableRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Notation createNotation(Notation notation, long clientId) {
        ClientRetail client = (ClientRetail) clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        List<Response> responses = notation.getResponses();
        notation.setClient(client);
        notation.setResponses(null); // Temporary null to avoid double save
        notation.setStatus(ResponseStatus.IN_PROGRESS);
        double totalScore = 0;
        for (Response response : responses) {
            Double score = calculateNote(response);
            if (score == null) {
                System.out.println("Score is null for response ID: " + response.getId());
                continue; // Skip null scores
            }
            totalScore += score;
            response.setNotation(notation);
        }
        System.out.println("Total Score: " + totalScore);
        notation.setNote(totalScore);

        // Générer et définir l'appréciation
        Appreciation appreciation = generateAppreciation(totalScore);
        notation.setAppreciation(appreciation);
        System.out.println("Appreciation set: " + notation.getAppreciation()); // Log appreciation

        Notation savedNotation = notationRepository.save(notation);
        for (Response response : responses) {
            response.setNotation(savedNotation);
            responseRepository.save(response);
        }
        if (client.getNotations() == null) {
            client.setNotations(new ArrayList<>());
        }
        client.getNotations().add(savedNotation);
        clientRepository.save(client);

        return savedNotation;
    }

    public Appreciation generateAppreciation(double note) {
        if (note >= 0.8) {
            return Appreciation.TRES_BON;
        } else if (note >= 0.6) {
            return Appreciation.BON;
        } else if (note >= 0.4) {
            return Appreciation.MOYEN;
        } else if (note >= 0.2) {
            return Appreciation.FAIBLE;
        } else {
            return Appreciation.TRES_FAIBLE;
        }
    }




    public Notation finalizeNotation(long notationId) {
        Notation notation = notationRepository.findById(notationId)
                .orElseThrow(() -> new EntityNotFoundException("Notation not found"));

        // Set status to DONE (1)
        notation.setStatus(ResponseStatus.DONE);
        return notationRepository.save(notation);
    }

    public Notation updateNotation(NotationDto notationDto) {
        Notation notation = notationRepository.findById(notationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notation not found"));

        // Mettre à jour les réponses si elles sont présentes
        List<Response> responseList = notation.getResponses();
        List<ResponseDto> updateResponses = notationDto.getResponses();

        if (responseList.size() != updateResponses.size()) {
            throw new IllegalArgumentException("Mismatch in responses count");
        }

        for (int i = 0; i < responseList.size(); i++) {
            responseList.get(i).setResponse(updateResponses.get(i).getResponse());
        }

        // Mise à jour du statut et de la note
        notation.setStatus(notationDto.getStatus());
        notation.setNote(notationDto.getNote());

        // Sauvegarde la notation mise à jour
        return notationRepository.save(notation);
    }

    /*public Notation updateNotation(NotationDto notationDto) {
        System.out.println("Received Notation ID: " + notationDto.getId());

        Notation existingNotation = notationRepository.findById(notationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notation not found"));
        System.out.println("Notation trouvée: " + existingNotation);

        long clientId = existingNotation.getClient().getId();
        System.out.println("Client ID from Notation: " + clientId);

        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (!clientOpt.isPresent()) {
            System.out.println("Client avec l'ID " + clientId + " non trouvé.");
            throw new EntityNotFoundException("Client not found");
        }
        Client client = clientOpt.get();
        System.out.println("Client trouvé: " + client);

        // Filtrer les réponses invalides
        List<Response> responses = notationDto.getResponses().stream()
                .filter(responseDto -> responseDto.getResponseText() != null)
                .map(responseDto -> {
                    Response response = new Response();
                    response.setId(responseDto.getId());
                    response.setVariableId(responseDto.getVariableId());
                    response.setResponse(responseDto.getResponseText());
                    response.setNotation(existingNotation);
                    System.out.println("Mapping response: " + response);
                    return response;
                }).collect(Collectors.toList());

        existingNotation.setStatus(notationDto.getStatus());
        double totalScore = 0;
        for (Response response : responses) {
            totalScore += calculateNote(response);
            System.out.println("Calculated note for response: " + response.getId() + " - Score: " + totalScore);
        }
        existingNotation.setNote(totalScore);

        existingNotation.setResponses(responses);
        Notation savedNotation = notationRepository.save(existingNotation);

        for (Response response : responses) {
            response.setNotation(savedNotation);
            System.out.println("Updated response: " + response.getId());
        }

        notationRepository.save(savedNotation);
        clientRepository.save(client);

        System.out.println("La note mise à jour est : " + savedNotation.getNote());

        return savedNotation;
    }*/
    public List<VariableResponse> getInProgress(long id) {
        List<Response> responses = notationRepository.findById(id).get().getResponses();
        List<VariableResponse> variableResponses = new ArrayList<>();
        List<Long> done = new ArrayList<>();

        for (Response response : responses) {
            Variable variable = variableRepository.findById(response.getVariableId()).get();
            variableResponses.add(new VariableResponse(variable.getId(), variable.getCode(), variable.getDescription(), variable.getCoefficient(), response.getResponse(), variable.getType()));
            done.add(variable.getId());
        }

        List<Variable> variables = variableRepository.findAll();

        for (Variable variable : variables) {
            if (!done.contains(variable.getId())) {
                variableResponses.add(new VariableResponse(variable.getId(), variable.getCode(), variable.getDescription(), variable.getCoefficient(), null, variable.getType()));
            }
        }

        return variableResponses;
    }

    public List<NotationQuest> getTerminated() {
        List<Notation> notations = notationRepository.findByStatus(ResponseStatus.DONE);

        // Utiliser un `Map` pour grouper les notations par client
        return notations.stream()
                .collect(Collectors.toMap(
                        notation -> notation.getClient().getId(), // Clé: ID du client
                        notation -> notation, // Valeur: Notation
                        (existing, replacement) -> existing // Garde la première occurrence de la notation
                ))
                .values().stream()
                .map(notation -> {
                    List<ResponseQuest> responseQuests = notation.getResponses().stream()
                            .map(response -> new ResponseQuest(
                                    response.getId(),
                                    response.getVariableId(),
                                    response.getResponse(),
                                    variableRepository.findById(response.getVariableId()).get().getDescription()))
                            .toList();

                    return new NotationQuest(
                            notation.getId(),
                            notation.getStatus(),
                            notation.getNote(),
                            responseQuests,
                            notation.getClient().getId(),
                            notation.getClient().getNom(),
                            notation.getCreatedDate()
                    );
                })
                .toList();
    }


    public List<Notation> getInProgress() {
        return notationRepository.findByStatus(ResponseStatus.IN_PROGRESS);
    }


    // HEDHI NOTE FINAL ( bouton noter)
    public Notation determineNote(Notation notation, long id) {
        ClientRetail client = (ClientRetail) clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));

        // Calcul de la note en fonction des réponses
        Double note = notation.getResponses().stream()
                .map(this::calculateNote)
                .reduce(0.0, Double::sum);
        System.out.println("Note calculée: " + note);
        notation.setNote(note);

        // Générer et définir l'appréciation
        Appreciation appreciation = generateAppreciation(note);
        notation.setAppreciation(appreciation);
        System.out.println("Appréciation générée: " + appreciation); // Log pour vérification

        // Liaison de la notation avec le client
        notation.setClient(client);

        // Sauvegarde de la notation et des réponses associées
        List<Response> responses = notation.getResponses();

        // S'assurer que les réponses sont liées à la notation avant la sauvegarde
        responses.forEach(response -> response.setNotation(notation));

        // Ajouter la nouvelle notation au client
        if (client.getNotations() == null) {
            client.setNotations(new ArrayList<>());
        }
        client.getNotations().add(notation);

        // Sauvegarder le client et la notation
        clientRepository.save(client);

        // La notation avec les réponses est sauvegardée en cascade
        return notationRepository.save(notation);
    }

    public Notation determineNoteProgress(Notation notation, long id) {
        ClientRetail client = (ClientRetail) clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));

        // Calcul de la note en fonction des réponses
        Double note = notation.getResponses().stream()
                .map(this::calculateNote)
                .reduce(0.0, Double::sum);
        System.out.println("Note calculée: " + note);
        notation.setNote(note);

        // Générer et définir l'appréciation
        Appreciation appreciation = generateAppreciation(note);
        notation.setAppreciation(appreciation);
        System.out.println("Appréciation générée: " + appreciation); // Log pour vérification

        // Liaison de la notation avec le client
        notation.setClient(client);

        // Sauvegarde de la notation et des réponses associées
        List<Response> responses = notation.getResponses();

        // S'assurer que les réponses sont liées à la notation avant la sauvegarde
        responses.forEach(response -> response.setNotation(notation));

        // Ajouter la nouvelle notation au client
        if (client.getNotations() == null) {
            client.setNotations(new ArrayList<>());
        }
        client.getNotations().add(notation);

        // Sauvegarder le client et la notation
        clientRepository.save(client);

        // La notation avec les réponses est sauvegardée en cascade
        return notationRepository.save(notation);
    }



    public Notation determineNotea(Notation notation) {
        // Vérification que le client existe dans la base de données
        Client client = clientRepository.findById(notation.getClient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé."));
        notation.setClient(client);

        // Calcul de la note
        Double note = notation.getResponses().stream()
                .map(this::calculateNote)
                .reduce(0.0, Double::sum);
        notation.setNote(note);

        // Traitement des réponses
        List<Response> responses = notation.getResponses();
        for (Response response : responses) {
            response.setNotation(notation);
        }

        // Enregistrement de la notation et des réponses
        Notation savedNotation = notationRepository.save(notation);
        for (Response response : responses) {
            response.setNotation(savedNotation);
            responseRepository.save(response); // Sauvegarder chaque réponse
        }

        return savedNotation;
    }
    private double calculateNote(Response response) {
       /* System.out.println("Calculating note for response ID: " + response.getId());
        System.out.println("Variable ID: " + response.getVariableId());
        System.out.println("Response Text: " + response.getResponse());*/

        Variable variable = variableRepository.findById(response.getVariableId())
                .orElseThrow(() -> new EntityNotFoundException("Variable not found"));
        System.out.println("Variable found: " + variable);

        Double score = null;

        if (response.getResponse() == null) {
            throw new IllegalArgumentException("Response text is null for response ID: " + response.getId());
        }

        if (NUMBER.equals(variable.getType())) {
            score = variable.getScores().stream()
                    .filter(score1 -> isMatchingNumber(score1, response))
                    .findFirst()
                    .map(Score::getScore)
                    .orElse(null);
            System.out.println("Checking NUMBER type. Score found: " + score);
        } else if (ENUMERATION.equals(variable.getType())) {
            score = variable.getScores().stream()
                    .filter(score1 -> isMatchingEnumeration(score1, response))
                    .findFirst()
                    .map(Score::getScore)
                    .orElse(null);
            System.out.println("Checking ENUMERATION type. Score found: " + score);
        } else if (INTERVALE.equals(variable.getType())) {
            score = variable.getScores().stream()
                    .filter(score1 -> isBetweenInterval(score1, response))
                    .findFirst()
                    .map(Score::getScore)
                    .orElse(null);
            System.out.println("Checking INTERVAL type. Score found: " + score);
        } else if (DATE.equals(variable.getType())) {
            score = variable.getScores().stream()
                    .filter(score1 -> {
                        try {
                            return isMatchingDate(score1, response);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .findFirst()
                    .map(Score::getScore)
                    .orElse(null);
            System.out.println("Checking DATE type. Score found: " + score);
        } else {
            throw new IllegalArgumentException("Unknown variable type: " + variable.getType());
        }

        if (score == null) {
            System.out.println("No matching score found for response ID: " + response.getId() + " with variable ID: " + response.getVariableId());
            return 0.0;
        }

        System.out.println("Score found: " + score);
        return score * variable.getCoefficient();
    }


    public boolean isMatchingNumber(Score score, Response response) {
        System.out.println("Checking NUMBER match for response ID: " + response.getId());
        System.out.println("Score value: " + ((NUMBER) score).getValeur());
        System.out.println("Response value: " + Double.parseDouble(response.getResponse()));

        double targetValue = Double.parseDouble(response.getResponse());
        NUMBER number = (NUMBER) score;
        return number.getValeur().equals(targetValue);
    }


    public boolean isMatchingEnumeration(Score score, Response response) {
        System.out.println("Checking ENUMERATION match for response ID: " + response.getId());
        System.out.println("Score value: " + ((ENUMERATION) score).getValeur());
        System.out.println("Response value: " + response.getResponse());

        ENUMERATION enumeration = (ENUMERATION) score;
        return enumeration.getValeur().equals(response.getResponse());
    }

    public boolean isBetweenInterval(Score score, Response response) {
        System.out.println("Checking INTERVAL match for response ID: " + response.getId());
        System.out.println("Score min value: " + ((INTERVALE) score).getvMin());
        System.out.println("Score max value: " + ((INTERVALE) score).getvMax());
        System.out.println("Response value: " + Double.parseDouble(response.getResponse()));

        INTERVALE intervale = (INTERVALE) score;
        double vMin = Double.parseDouble(intervale.getvMin());
        double vMax = Double.parseDouble(intervale.getvMax());
        double responseValue = Double.parseDouble(response.getResponse());
        return vMin <= responseValue && responseValue <= vMax;
    }

    public boolean isMatchingDate(Score score, Response response) throws ParseException {
        System.out.println("Checking DATE match for response ID: " + response.getId());

        if (!(score instanceof DATE)) {
            System.out.println("Score is not of type DATE");
            return false;  // ou lever une exception si c'est un cas critique
        }

        DATE date = (DATE) score;
        System.out.println("Score value: " + date.getValeur());
        System.out.println("Response value: " + response.getResponse());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        Date responseDate = formatter.parse(response.getResponse());

        // Comparaison des dates sans tenir compte de l'heure
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date.getValeur());
        cal2.setTime(responseDate);

        boolean isSameDay = (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
                (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));

        return isSameDay;
    }



    public Notation getNotationById(Long id) {
        return notationRepository.findById(id)
                .orElse(null);
    }

    public int getProgressForClient(Long clientId) {
        List<Notation> notations = notationRepository.findByClientId(clientId); // Récupère les notations
        if (notations != null && !notations.isEmpty()) {
            // Supposons que nous prenons la première notation pour la progression
            return notations.get(0).getProgressPercentage(); // Appelle la méthode de progression
        }
        return -1; // Indique que la notation n'a pas été trouvée
    }
}
