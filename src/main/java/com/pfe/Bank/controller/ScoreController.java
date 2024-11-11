package com.pfe.Bank.controller;

import com.pfe.Bank.dto.DateUtils;
import com.pfe.Bank.dto.ResponseDto;
import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.model.ResponseStatus;
import com.pfe.Bank.repository.ClientRepository;
import com.pfe.Bank.repository.NotationRepository;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import com.pfe.Bank.service.CalculScoreService;
import com.pfe.Bank.service.ClientService;
import com.pfe.Bank.service.NotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ScoreController {
    @Autowired
    CalculScoreService calculScoreService;
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    ScoreVariableRepository scoreVariableRepository;
    @Autowired
    NotationService notationService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    NotationRepository notationRepository;
    @PostMapping("/addScore")
    public ResponseEntity<?> addScore(@RequestBody ScoreDto scoreDto) {
        Variable variable = variableRepository.findById(scoreDto.getVariableId())
                .orElseThrow(() -> new EntityNotFoundException("Variable not found"));

        Score score;

        switch (variable.getType()) {
            case INTERVALE:
                if (scoreDto.getVmin() == null || scoreDto.getVmax() == null) {
                    return ResponseEntity.badRequest().body("vMin and vMax are required for type INTERVAL");
                }
                INTERVALE intervalScore = new INTERVALE();
                intervalScore.setvMin(scoreDto.getVmin());
                intervalScore.setvMax(scoreDto.getVmax());
                intervalScore.setScore(scoreDto.getScore());
                intervalScore.setVariable(variable);
                score = intervalScore;
                break;

            case ENUMERATION:
                if (scoreDto.getEnumeration() == null) {
                    return ResponseEntity.badRequest().body("Enumeration value is required for type ENUMERATION");
                }
                ENUMERATION enumScore = new ENUMERATION();
                enumScore.setValeur(scoreDto.getEnumeration());
                enumScore.setScore(scoreDto.getScore());
                enumScore.setVariable(variable);
                score = enumScore;
                break;

            case DATE:
                if (scoreDto.getDate() == null) {
                    return ResponseEntity.badRequest().body("Date is required for type DATE");
                }
                DATE dateScore = new DATE();
                dateScore.setValeur(scoreDto.getDate());
                dateScore.setScore(scoreDto.getScore());
                dateScore.setVariable(variable);
                score = dateScore;
                break;

            case NUMBER:
                if (scoreDto.getNum() == null) {
                    return ResponseEntity.badRequest().body("Number value is required for type NUMBER");
                }
                NUMBER numberScore = new NUMBER();
                numberScore.setValeur(scoreDto.getNum());
                numberScore.setScore(scoreDto.getScore());
                numberScore.setVariable(variable);
                score = numberScore;
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid variable type");
        }

        // Log details before saving
        System.out.println("Saving score: " + score);
        System.out.println("Score details - ID: " + score.getId() + ", Score: " + score.getScore());

        scoreVariableRepository.save(score);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/CalculeScore")
    public ResponseEntity<Double> calculateScore(@RequestBody String values) {
        double score = calculScoreService.calculateScore(values);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/scores/{id}")
    public ResponseEntity<ScoreDto> getScoreById(@PathVariable Long id) {
        Score score = calculScoreService.findById(id)
                .orElseThrow(() -> new RuntimeException("Score not found with id: " + id));

        ScoreDto scoreDto = new ScoreDto().convertToDto(score);
        scoreDto.setType(score.getClass().getSimpleName());  // Mettez à jour le type si nécessaire

        return ResponseEntity.ok(scoreDto);
    }


    @PutMapping("/updateScore/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable Long id, @RequestBody Map<String, Object> scoreData) {
        try {
            String type = (String) scoreData.get("type");
            if (type == null) {
                throw new IllegalArgumentException("Score type is required");
            }

            Score updatedScore;

            switch (type) {
                case "NUMBER":
                    updatedScore = new NUMBER();
                    ((NUMBER) updatedScore).setValeur(Double.parseDouble(scoreData.get("valeur").toString()));
                    break;
                case "ENUMERATION":
                    updatedScore = new ENUMERATION();
                    ((ENUMERATION) updatedScore).setValeur((String) scoreData.get("valeur"));
                    break;
                case "INTERVALE":
                    updatedScore = new INTERVALE();
                    ((INTERVALE) updatedScore).setvMin(scoreData.get("vmin").toString());
                    ((INTERVALE) updatedScore).setvMax(scoreData.get("vmax").toString());
                    break;
                case "DATE":
                    updatedScore = new DATE();
                    LocalDate localDate = LocalDate.parse(scoreData.get("valeur").toString());
                    ((DATE) updatedScore).setValeur(DateUtils.convertToDateViaInstant(localDate));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported score type");
            }

            if (!scoreData.containsKey("score")) {
                throw new IllegalArgumentException("Score value is required");
            }

            updatedScore.setId(id);
            updatedScore.setScore(Double.parseDouble(scoreData.get("score").toString()));

            Score updated = calculScoreService.updateScore(id, updatedScore);

            if (updated instanceof DATE) {
                java.util.Date date = ((DATE) updated).getValeur();
                String formattedDate = DateUtils.formatDate(date);
                LocalDate localDateFormatted = LocalDate.parse(formattedDate);
                ((DATE) updated).setValeur(DateUtils.convertToDateViaInstant(localDateFormatted));
            }

            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteScore/{id}")
    public Map<String,Boolean> deleteScore(@PathVariable Long id) throws MissingEntity {
        return calculScoreService.deleteScore(id);
    }

    // new

// HEDHIII

    @PostMapping("notation/{clientId}")
    public Notation saveNotation(@RequestBody Notation notation, @PathVariable Long clientId) {
        return notationService.createNotation(notation, clientId);
    }
    @PostMapping("/finaliseNote/{clientId}")
    public ResponseEntity<Notation> createNotation(@PathVariable long clientId, @RequestBody Notation notation) {
        try {
            // Vérification s'il existe déjà une notation en cours pour ce client
            List<Notation> inProgressNotations = notationRepository.findByClientIdAndStatus(clientId, ResponseStatus.IN_PROGRESS);

            // Si une notation IN_PROGRESS existe déjà, ne pas en créer une nouvelle
            if (!inProgressNotations.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Vous devez finaliser votre note en cours avant d'en affecter une nouvelle.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Return the error response separately
            }

            // Sinon, procéder à la création de la nouvelle notation
            Notation savedNotation = notationService.determineNote(notation, clientId);
            return ResponseEntity.ok(savedNotation);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/Progress/{clientId}")
    public ResponseEntity<Notation> Progress(@PathVariable long clientId, @RequestBody Notation notation) {
        try {
            Notation savedNotation = notationService.determineNoteProgress(notation, clientId);
            return ResponseEntity.ok(savedNotation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /*@PutMapping("/notation")
    public Notation updateNotation(@RequestBody NotationDto notation){
        return notationService.updateNotation(notation);
    }*/
    @PutMapping("/notation")
    public ResponseEntity<Double> updateNotation(@RequestBody NotationDto notationDto) {
        // Log to check the client ID
        System.out.println("Received request for Notation with ID: " + notationDto.getId());
        System.out.println("Received Client ID: " + notationDto.getClientId());

        Notation updatedNotation = notationService.updateNotation(notationDto);
        // Retourner uniquement la note mise à jour
        return ResponseEntity.ok(updatedNotation.getNote());
    }


    // HEDHII
    @PostMapping("/note/{clientId}")
    public Notation calculateNote(@RequestBody Notation notation,@PathVariable Long clientId){
        return notationService.determineNote(notation,clientId);
    }

    @PutMapping("/{notationId}/finalize")
    public ResponseEntity<Notation> finalizeNotation(@PathVariable long notationId) {
        Notation finalizedNotation = notationService.finalizeNotation(notationId);
        return ResponseEntity.ok(finalizedNotation);
    }

    @PostMapping("/note")
    public ResponseEntity<Notation> calculateNotea(@RequestBody Notation notation) {

        System.out.println("Received Notation: " + notation);
        System.out.println("Client ID: " + (notation.getClient() != null ? notation.getClient().getId() : "null"));

        if (notation.getClient() == null || notation.getClient().getId() == 0) {
            throw new IllegalArgumentException("Le client ne peut pas être null ou sans ID dans la notation.");
        }
        Notation savedNotation = notationService.determineNotea(notation);
        return ResponseEntity.ok(savedNotation);
    }




    @GetMapping("/done")
    public List<NotationQuest> getTerminatedations(){
        return notationService.getTerminated();
    }

    @GetMapping("/inProgress")
    public List<Notation> getInProgress(){
        return notationService.getInProgress();
    }

    @GetMapping("/variableResponses/{id}")
    public List<VariableResponse> getVariableResponses(@PathVariable Long id){
        return notationService.getInProgress(id);
    }


    @GetMapping("/getNotationById/{id}")
    public ResponseEntity<NotationDto> getNotationById(@PathVariable Long id) {
        System.out.println("Received request for Notation with ID: " + id);

        Notation notation = notationService.getNotationById(id);
        if (notation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        NotationDto notationDto = new NotationDto();
        notationDto.setId(notation.getId());
        notationDto.setNote(notation.getNote());
        notationDto.setClientId(notation.getClient().getId());
        notationDto.setStatus(notation.getStatus());
        notationDto.setNom((notation.getClient().getNom()));
        notationDto.setCodeRelation(notation.getClient().getCodeRelation());
        notationDto.setCreatedDate(notation.getCreatedDate());
        List<ResponseDto> responseDtos = notation.getResponses().stream()
                .map(response -> {
                    ResponseDto responseDto = new ResponseDto();
                    responseDto.setId(response.getId());
                    responseDto.setVariableId(response.getVariableId());
                    responseDto.setResponse(response.getResponse());

                    if (response.getVariable() != null) {
                        VariableDto variableDto = new VariableDto();
                        variableDto.setId(response.getVariable().getId());
                        variableDto.setDescription(response.getVariable().getDescription());

                        responseDto.setVariable(variableDto);
                    }

                    return responseDto;
                }).collect(Collectors.toList());

        notationDto.setResponses(responseDtos);

        return ResponseEntity.ok(notationDto);
    }
@GetMapping("/getClientWithScore")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClientsWithNotations();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Notation>> getNotationsByClient(@PathVariable Long clientId) {
        List<Notation> notations = clientService.getNotationsByClientId(clientId);
        return ResponseEntity.ok(notations);
    }
   /* @GetMapping("/with-notations")
    public ResponseEntity<List<Client>> getAllClientsNotter() {
        List<Client> clients = clientService.getAllClientsWithNotations();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }*/
}