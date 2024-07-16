package com.pfe.Bank.controller;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import com.pfe.Bank.service.CalculScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<Score> addScore(@RequestBody ScoreDto variableRequest) {
        try {

            if (variableRequest.getVariableId() == null) {
                throw new IllegalArgumentException("Variable ID must not be null!");
            }

            Variable variable = variableRepository.findById(variableRequest.getVariableId())
                    .orElseThrow(() -> new EntityNotFoundException("Variable not found with id: " + variableRequest.getVariableId()));

            Score scores = new Score();
            scores.setScore(variableRequest.getScore());
            scores.setValeur(variableRequest.getValeur());
            scores.setVariable(variable);

            /*System.out.println("Score: " + scores.getScore());
            System.out.println("Valeur: " + scores.getValeur());
            System.out.println("Variable: " + scores.getVariable());*/

            Score createdScore = calculScoreService.addScore(scores);

            return ResponseEntity.ok(createdScore);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
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
