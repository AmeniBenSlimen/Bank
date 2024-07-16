package com.pfe.Bank.controller;

import com.pfe.Bank.dto.MenuDto;
import com.pfe.Bank.dto.RoleDto;
import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ModeleRepository;
import com.pfe.Bank.repository.VariableRepository;
import com.pfe.Bank.service.VariableService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @PostMapping("/addVariable")
    public ResponseEntity<Variable> addVariable(@RequestBody VariableDto variableRequest) {
        try {
            Modele modele = modeleRepository.findByUsedTrue()
                    .orElseThrow(() -> new EntityNotFoundException("No active Modele found"));

            Variable variable = new Variable();
            variable.setCode(variableRequest.getCode());
            variable.setDescription(variableRequest.getDescription());
            variable.setCoefficient(variableRequest.getCoefficient());
            variable.setType(variableRequest.getType());
            variable.setModele(modele);

            Variable createdVariable = variableService.addVariable(variable);

            return ResponseEntity.ok(createdVariable);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAllVariables")
    public ResponseEntity<List<VariableDto>> getAllVariablesWithScores() {
        List<Variable> variables = variableService.getAllVariable();
        Modele modele = modeleRepository.findByUsedTrue()
                .orElseThrow(() -> new EntityNotFoundException("No active Modele found"));

        List<VariableDto> variableDtos = variables.stream().map(variable -> {
            VariableDto variableDto = new VariableDto();
            variableDto.setId(variable.getId());
            variableDto.setCode(variable.getCode());
            variableDto.setCoefficient(variable.getCoefficient());
            variableDto.setType(variable.getType());
            variableDto.setDescription(variable.getDescription());
            variableDto.setModelId(modele.getId());

            List<ScoreDto> scoreDtos = variable.getScores().stream()
                    .map(score -> {
                        ScoreDto scoreDto = new ScoreDto();
                        scoreDto.setId(score.getId());
                        scoreDto.setScore(score.getScore());
                        scoreDto.setValeur(score.getValeur());
                        return scoreDto;
                    }).collect(Collectors.toList());

            variableDto.setScores(scoreDtos);
            return variableDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(variableDtos);
    }

    @GetMapping("/getVariableById/{id}")
    public ResponseEntity<VariableDto> getVariableWithScores(@PathVariable Long id) {
        try {
            Modele modele = modeleRepository.findByUsedTrue()
                    .orElseThrow(() -> new EntityNotFoundException("No active Modele found"));

            Variable variable = variableService.getVariableWithScores(id)
                    .orElseThrow(() -> new EntityNotFoundException("Variable not found"));

            VariableDto variableDto = new VariableDto();
            variableDto.setId(variable.getId());
            variableDto.setCode(variable.getCode());
            variableDto.setCoefficient(variable.getCoefficient());
            variableDto.setType(variable.getType());
            variableDto.setDescription(variable.getDescription());
            variableDto.setModelId(modele.getId());

            List<ScoreDto> scoreDtos = variable.getScores().stream()
                    .map(score -> {
                        ScoreDto scoreDto = new ScoreDto();
                        scoreDto.setId(score.getId());
                        scoreDto.setScore(score.getScore());
                        scoreDto.setValeur(score.getValeur());
                        scoreDto.setVariableId(score.getId());
                        return scoreDto;
                    }).collect(Collectors.toList());

            variableDto.setScores(scoreDtos);
            return ResponseEntity.ok(variableDto);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updataVariable/{id}")
    public ResponseEntity<Variable> updateVariable(@PathVariable Long id, @RequestBody Variable updatedVariable) {
        Variable updated = variableService.updateVariable(id, updatedVariable);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/calculateScore")
    public double calculateScore(@RequestBody List<String> values) {
        return variableService.calculateScore(values);
    }

}

