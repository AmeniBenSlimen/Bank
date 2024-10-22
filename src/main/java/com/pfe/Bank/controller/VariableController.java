package com.pfe.Bank.controller;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.*;
import com.pfe.Bank.service.CalculScoreService;
import com.pfe.Bank.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pfe.Bank.model.Type.ENUMERATION;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class VariableController {
    @Autowired
    VariableService variableService;
    @Autowired
    ModeleRepository modeleRepository;
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    CalculScoreService calculScoreService;
    @Autowired
    ScoreVariableRepository scoreVariableRepository;
    @Autowired
    ClientRetailRepository clientRetailRepository;

    @Autowired
    SituationClientRepository situationClientRepository;
    @Autowired
    ClientRepository clientRepository;
    @PostMapping("/addVariable/{modelId}")
    public ResponseEntity<Variable> addVariable(@RequestBody VariableDto variableRequest, @PathVariable long modelId) {
        try {
            Modele modele = modeleRepository.findById(modelId)
                    .orElseThrow(() -> new EntityNotFoundException("No active Modele found with ID: " + modelId));

            Variable variable = new Variable();
            variable.setCode(variableRequest.getCode());
            variable.setDescription(variableRequest.getDescription());
            variable.setCoefficient(variableRequest.getCoefficient());
            variable.setResponseMeaning(variableRequest.getResponseMeaning());
            variable.setType(variableRequest.getType());
            variable.setModele(modele);

            Variable createdVariable = variableService.createVariable(variable,modelId);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdVariable);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /* @PostMapping("/addVariable")
  public ResponseEntity<Variable> createVariable(@RequestBody Variable variable) {
      Variable createdVariable = variableService.createVariable(variable);
      return new ResponseEntity<>(createdVariable, HttpStatus.CREATED);
  }*/
    @PostMapping("/{variableId}/scores")
    public ResponseEntity<Score> addScoreToVariable(
            @PathVariable long variableId,
            @RequestBody Score score) {
        try {
            variableService.addScoreToVariable(variableId, score);
            return new ResponseEntity<>(score, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAllVariables")
    public ResponseEntity<List<VariableDto>> getAllVariablesWithScores() {
        List<Variable> variables = variableService.getAllVariable();
        /*Modele modele = modeleRepository.findByUsedTrue()
                .orElseThrow(() -> new EntityNotFoundException("No active Modele found"));*/
        System.out.println(variables.toString());

        List<VariableDto> variableDtos = variables.stream().map(variable -> {
            VariableDto variableDto = new VariableDto();
            variableDto.setId(variable.getId());
            variableDto.setCode(variable.getCode());
            variableDto.setCoefficient(variable.getCoefficient());
            variableDto.setType(variable.getType());
            variableDto.setDescription(variable.getDescription());
            //variableDto.setModelId(variable.getModele().getId());

            if(ENUMERATION.equals(variable.getType())) {
                List<ScoreDto> scoreDtos = variable.getScores().stream()
                        .map(score -> {
                            com.pfe.Bank.model.ENUMERATION enumeration = (ENUMERATION) score;
                            ScoreDto scoreDto = new ScoreDto();
                            scoreDto.setId(score.getId());
                            scoreDto.setScore(score.getScore());
                            scoreDto.setValeur(enumeration.getValeur());
                            return scoreDto;
                        }).collect(Collectors.toList());

                variableDto.setScores(scoreDtos);
            }
            System.out.println(variable.getScores().toString());

            return variableDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(variableDtos);
    }

    @GetMapping("/getVariableScoreById/{id}")
    public ResponseEntity<VariableDto> getVariableWithScores(@PathVariable Long id) throws MissingEntity {
        Variable variable = variableService.findById(id);
        List<ScoreDto> scoreDtos = variableService.getScoresByVariableId(id);

        VariableDto variableDto = new VariableDto();
        variableDto.setId(variable.getId());
        variableDto.setCode(variable.getCode());
        variableDto.setDescription(variable.getDescription());
        variableDto.setCoefficient(variable.getCoefficient());
        variableDto.setType(variable.getType());
        variableDto.setModelId(variable.getModele().getId());
        variableDto.setScores(scoreDtos);
        return ResponseEntity.ok(variableDto);
    }
    @PutMapping("/updataVariable/{id}")
    public ResponseEntity<Variable> updateVariable(@PathVariable Long id, @RequestBody Variable updatedVariable) {
        Variable updateVariable = variableService.updateVariable(id, updatedVariable);
        return ResponseEntity.ok(updateVariable);
    }


    @DeleteMapping("/deleteVariable/{id}")
    public ResponseEntity<Void> deleteVariable(@PathVariable Long id) {
        try {
            variableService.deleteVariable(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/ponderation/{id}")
    public double getPonderationForVariable(@PathVariable Long id) {
        return variableService.calculatePonderationForVariable(id);
    }
    @GetMapping("/VariableModele/{modeleId}")
    public List<VariableDto> getVariablesByModeleId(@PathVariable Long modeleId) {
        return variableService.getVariablesByModeleId(modeleId);
    }
    @GetMapping("/getAllVariables/{clientId}")
    public ResponseEntity<List<VariableDto>> getAllVariablesWithScores(@PathVariable long clientId) {
        List<Variable> variables = variableService.getVariablesByActiveModele();
        System.out.println(variables.toString());

        // Récupération du ClientRetail
        ClientRetail clientRetail = clientRetailRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client Retail not found with ID: " + clientId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client Retail not found with ID: " + clientId));
        // Récupération de la situation client
        List<SituationClientRetail> situations = situationClientRepository.findByCodeRelation(clientRetail.getCodeRelation());
        SituationClientRetail situationClientRetail = situations.isEmpty() ? null : situations.get(0);

        // Mapper les variables en VariableDto
        List<VariableDto> variableDtos = variables.stream().map(variable -> {
            System.out.println("Response Meaning: " + variable.getResponseMeaning());
            VariableDto variableDto = new VariableDto();
            variableDto.setId(variable.getId());
            variableDto.setCode(variable.getCode());
            variableDto.setCoefficient(variable.getCoefficient());
            variableDto.setType(variable.getType());
            variableDto.setDescription(variable.getDescription());
            variableDto.setResponseMeaning(variable.getResponseMeaning());
            // Si la variable est de type ENUMERATION, mapper les scores
            if (ENUMERATION.equals(variable.getType())) {
                List<ScoreDto> scoreDtos = variable.getScores().stream()
                        .map(score -> {
                            com.pfe.Bank.model.ENUMERATION enumeration = (ENUMERATION) score;
                            ScoreDto scoreDto = new ScoreDto();
                            scoreDto.setId(score.getId());
                            scoreDto.setScore(score.getScore());
                            scoreDto.setValeur(enumeration.getValeur());
                            return scoreDto;
                        }).collect(Collectors.toList());
                variableDto.setScores(scoreDtos);
            }

            // Mapper la réponse en fonction de la signification de la variable
            if (Objects.nonNull(variable.getResponseMeaning())) {
                switch (variable.getResponseMeaning()) {
                    case PROFESSION:
                        variableDto.setResponse(clientRetail.getProfession());
                        break;
                    case NATIONALITE:
                        variableDto.setResponse(clientRetail.getNationalite() != null ? clientRetail.getNationalite().toString() : null);
                        break;
                    case SALAIREDOMICILE:
                        Double salaireDomicile = clientRetail.getSalaireDomicile(); // Utiliser Double
                        variableDto.setResponse(String.valueOf(salaireDomicile));
                        break;
                    case ADRESSE:
                        variableDto.setResponse(client.getAdresse() != null ? client.getAdresse().toString() : null);
                        break;
                    case AGENCE:
                        variableDto.setResponse(String.valueOf(client.getAgence()));
                        break;
                    case AUTREINFORMATION:
                        variableDto.setResponse(String.valueOf(client.getAutre()));
                        break;
                    case CODERELATION:
                        variableDto.setResponse(String.valueOf(client.getCodeRelation()));
                        break;
                    case CODERELATIONFLEXCUBE:
                        variableDto.setResponse(String.valueOf(client.getCodeRelationFlexcube()));
                        break;
                    case IDENTIFIANTPROSPECT:
                        variableDto.setResponse(String.valueOf(client.getIdentifiantProspect()));
                        break;
                    case NOM:
                        variableDto.setResponse(String.valueOf(client.getNom()));
                        break;
                    case REGION:
                        variableDto.setResponse(String.valueOf(client.getRegion()));
                        break;
                    case VILLE:
                        variableDto.setResponse(String.valueOf(client.getVille()));
                        break;
                    case DATEEMBAUCHE :
                        variableDto.setResponse(String.valueOf(clientRetail.getDateEmbauche()));
                        break;
                    case NEWMODELUBCI:
                        variableDto.setResponse(String.valueOf(clientRetail.getNewModelUBCI()));
                        break;
                    case NEWPROFESSIONCODE:
                        variableDto.setResponse(String.valueOf(clientRetail.getNewProfessionCode()));
                        break;
                    case DATEDEBUTRELATION:
                        variableDto.setResponse(String.valueOf(clientRetail.getDateDebutRelation()));
                        break;
                    case SITUATIONFAMILIALE:
                        variableDto.setResponse(String.valueOf(clientRetail.getSituationFamiliale()));
                        break;
                    case ISFULL:
                        variableDto.setResponse(String.valueOf(clientRetail.isIsfull()));
                        break;
                    //case DATEDEBUTRELATION:
                        // Récupérer la date de début de relation
                       // Date dateDebutRelation = client.getDateDebutRelation();
                       /* variableDto.setResponse(dateDebutRelation != null
                                ? new SimpleDateFormat("yyyy-MM-dd").format(dateDebutRelation)
                                : "Non spécifié");
                        break;*/
                    case DATENAISSANCE:
                        variableDto.setResponse(clientRetail.getDateNaissance() != null ? clientRetail.getDateNaissance().toString() : null);
                        break;
                    case MntEnConsolidation:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMntEnConsolidation()) : null);
                        break;
                    case ANCIENNETEIMPAYES:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getAncienneteImpayes()) : null);
                        break;
                    case IMPAYE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getImpaye()) : null);
                        break;
                    case SOLDEMOYENANNUELANNEEN:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getSoldeMoyenAnnuelAnneeN()) : null);
                        break;
                    case MONTANTIMPAYES:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMontantImpayes()) : null);
                        break;
                    case REGULARITEECHEANCES:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRegulariteEcheances()) : null);
                        break;
                    case RATIOENGAGEMENTCDR:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRatioEngagementCDR()) : null);
                        break;
                    case SOLDEMOYENANNUEANNEEN1:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getSoldeMoyenAnnuelAnneeN1()) : null);
                        break;
                    case CLASSERISQUELEGACY:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getClasseRisqueLegacy()) : null);
                        break;
                    case IDENTIFIANTNATIONAL:
                        variableDto.setResponse(String.valueOf(clientRetail.getIdNat()));
                        break;
                    case DATEDENAISSANCE:
                        variableDto.setResponse(String.valueOf(client.getDateNaissance()));
                        break;
                    case ANNEECLASSIFICATIONCENTREALE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getAnneeClassificationCentrale()) : null);
                        break;
                    case BESOINACCOMPAGNEMENT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getBesoinAccompagnement()) : null);
                        break;
                    case BESOINFINANCEMENT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getBesoinFinancement()) : null);
                        break;
                    case CLASSEBANKCENTRALE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getClasseBanqueCentrale()) : null);
                        break;
                    case CODEMATERIELLE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getCodeMaterielite()) : null);
                        break;
                    case CONSOLIDATIONAUTREBANQUE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getConsolidationAutresBanques()) : null);
                        break;
                    case DATEDESITUATION:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getDateDeSituation()) : null);
                        break;
                    case DATERATINGLEGACY:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getDateRatingLegacy()) : null);
                        break;
                    case DERNIERESALAIREYTD:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getDernierSalaireYTD()) : null);
                        break;
                    case ENCOURSCT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getEncoursCT()) : null);
                        break;
                    case ENCOURSCREDITTRESORERIE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getEncoursCreditTresorerie()) : null);
                        break;
                    case ENCOURSSMT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getEncoursMT()) : null);
                        break;
                    case IARCENTRALERISQUESCDR:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getIarCentralRisquesCDR()) : null);
                        break;
                    case INCIDENT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getIncident()) : null);
                        break;
                    case MNTENCONSOLIDATION:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMntEnConsolidation()) : null);
                        break;
                    case MODELEAPPLICABLE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getModeleApplicable()) : null);
                        break;
                    case MOUVEMENTCREDITEURANNEEN:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMouvementCreditieurAnneeN()) : null);
                        break;
                    case MOUVEMENTCREDITEURANNEEN1:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMouvementCreditieurAnneeN1()) : null);
                        break;
                    case MOUVEMENTDEBITEURANNEEN:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMouvementDebiteurAnneeN()) : null);
                        break;
                    case MOUVEMENTDEBITEURANNEEN1:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMouvementDebiteurAnneeN1()) : null);
                        break;
                    case MOUVEMENTSTOTAUXANNEEN:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMouvementsTotauxAnneeN()) : null);
                        break;
                    case MOUVEMENTSTOTAUXANNEEN1:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getMouvementsTotauxAnneeN1()) : null);
                        break;
                    case NUMEROCOMPTEPRINCIPALE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getNumeroComptePrincipal()) : null);
                        break;
                    case RATINGACTUELLELEGACY:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRatingActuelleLegacy()) : null);
                        break;
                    case RATIOCREDITSOLDEMOYEN:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRatioCreditSoldeMoyen()) : null);
                        break;
                    case RATIOIMPAYESENGAGEMENTS:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRatioImpayesEngagements()) : null);
                        break;
                    case RATIOENDETTEMRNT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRationEndettement()) : null);
                        break;
                    case RATIOSOLDEMOYENFC:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getRationSoldeMoyenFC()) : null);
                        break;
                    case SCORECLIENTLEGACY:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getScoreClientLegacy()) : null);
                        break;
                    case SOLDEMOYENANNUELANNEEN1:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getSoldeMoyenAnnuelAnneeN1()) : null);
                        break;
                    case TOTALCREANCESGLE:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getTotalCreancesGLE()) : null);
                        break;
                    case VARIATIONANNUELLEMVTCREDITN:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getVariationAnnuelleMvtCreditN()) : null);
                        break;
                    case VARIATIONANNUELLEMVTCREDITN1:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getVariationAnnuelleMvtCreditN1()) : null);
                        break;
                    case VARIATIONCREDITRESOCDR:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getVariationCreditTresoCDR()) : null);
                        break;
                    case VARIATIONENGAGEMENTCDR:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getClasseRisqueLegacy()) : null);
                        break;
                    case VARIATIONMVTCREDIT:
                        variableDto.setResponse(situationClientRetail != null ? String.valueOf(situationClientRetail.getVariationMvtCredit()) : null);
                        break;
                    default:
                        break;
                }
            }

            return variableDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(variableDtos);
    }

}

