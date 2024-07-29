package com.pfe.Bank.controller;

import com.pfe.Bank.dto.DateUtils;
import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import com.pfe.Bank.service.CalculScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Map;

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
        System.out.println("Score details - ID: " + score.getId() + ", Score: " + score.getScore() + ", Value: " + score.getValeur());

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
}