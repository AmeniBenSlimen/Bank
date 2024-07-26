package com.pfe.Bank.controller;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
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
import java.time.ZoneId;
import java.util.Date;
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
        // Récupération de la variable associée
        Variable variable = variableRepository.findById(scoreDto.getVariableId())
                .orElseThrow(() -> new EntityNotFoundException("Variable not found"));

        Score score;

        // Création du score en fonction du type de variable
        switch (variable.getType()) {
            case INTERVALE:
                if (scoreDto.getVmin() == null || scoreDto.getVmax() == null) {
                    return ResponseEntity.badRequest().body("vMin and vMax are required for type INTERVAL");
                }
                SVInterval intervalScore = new SVInterval();
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
                SVEnum enumScore = new SVEnum();
                enumScore.setValeur(scoreDto.getEnumeration());
                enumScore.setScore(scoreDto.getScore());
                enumScore.setVariable(variable);
                score = enumScore;
                break;

            case DATE:
                if (scoreDto.getDate() == null) {
                    return ResponseEntity.badRequest().body("Date is required for type DATE");
                }
                SVDate svDate = new SVDate();
                svDate.setValeur(scoreDto.getDate());
                svDate.setScore(scoreDto.getScore());
                svDate.setVariable(variable);
                score = svDate;
                break;

            case NUMBER:
                if (scoreDto.getNum() == null) {
                    return ResponseEntity.badRequest().body("Number value is required for type NUMBER");
                }
                SVNumber svNumber = new SVNumber();
                svNumber.setValeur(Double.valueOf(scoreDto.getNum()));
                svNumber.setScore(scoreDto.getScore());
                svNumber.setVariable(variable);
                score = svNumber;
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid variable type");
        }
        scoreVariableRepository.save(score);

        return ResponseEntity.ok().build();
    }





    @PostMapping("/CalculeScore")
    public ResponseEntity<Double> calculateScore(@RequestBody String values) {
        double score = calculScoreService.calculateScore(values);
        return ResponseEntity.ok(score);
    }
    @GetMapping("/scores/{id}")
    public ResponseEntity<Score> getScoreById(@PathVariable Long id) {
        Score score = scoreVariableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Score not found with id: " + id));
        return ResponseEntity.ok(score);
    }
    @PutMapping("/updataScore/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable Long id, @RequestBody Score updatedScore) {
        Score updated = calculScoreService.updateScore(id, updatedScore);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @DeleteMapping("/deleteScore/{id}")
    public Map<String,Boolean> deleteScore(@PathVariable Long id) throws MissingEntity {
        return calculScoreService.deleteScore(id);
    }
}
