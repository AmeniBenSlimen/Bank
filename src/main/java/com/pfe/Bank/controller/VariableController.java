package com.pfe.Bank.controller;

import com.pfe.Bank.dto.MenuDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Type;
import com.pfe.Bank.model.Variable;
import com.pfe.Bank.repository.ModeleRepository;
import com.pfe.Bank.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class VariableController {
    @Autowired
    VariableService variableService;
    @Autowired
    ModeleRepository modeleRepository;
    @PostMapping("/addVariable")
    public ResponseEntity<Variable> addVariable(@RequestBody VariableDto variableRequest) {
        try {
            System.out.println(variableRequest.getModelid());
            Modele modele = modeleRepository.findById(variableRequest.getModelid())
                    .orElseThrow(() -> new EntityNotFoundException("Modele not found with id: " + variableRequest.getModelid()));

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


}
