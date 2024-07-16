package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ModeleRepository;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VariableServiceImpl implements VariableService{
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    ModeleRepository modeleRepository;
    @Autowired
    ScoreVariableRepository scoreVariableRepository;
    @Override
    public Modele findById(Long id) throws MissingEntity {
        Optional<Modele> modele = modeleRepository.findById(id);
        if(!modele.isPresent()){
            throw new MissingEntity("Modele not found with Id Modele : "+id);
        }
        return modele.get();
    }
    @Override
    public Variable addVariable(Variable variable) {

            return variableRepository.save(variable);
    }

    @Override
    public List<Variable> getAllVariable() {
        return variableRepository.findAll();
    }

    @Override
    public Variable getVariableById(long id) throws MissingEntity {
        Optional<Variable> variable = variableRepository.findById(id);
        if(!variable.isPresent()){
            throw new MissingEntity("Variable not found with ID : "+id);
        }
        return variable.get();
    }
    public Optional<Variable> getVariableWithScores(Long id) {
        return variableRepository.findById(id);
    }

    @Override
    public Variable updateVariable(Long id, Variable updatedVariable) {
        if (updatedVariable == null) {
            throw new IllegalArgumentException("La variable mise à jour est null.");
        }

        Variable existingVariable = variableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variable not found with id: " + id));
        existingVariable.setCode(updatedVariable.getCode());
        existingVariable.setDescription(updatedVariable.getDescription());
        existingVariable.setCoefficient(updatedVariable.getCoefficient());
        existingVariable.setType(updatedVariable.getType());
        return variableRepository.save(existingVariable);
    }
    @Override
    public Variable save(Variable variable) {
        return variableRepository.save(variable);
    }
    @Override
    public Variable findById(long id) {
        return variableRepository.findById(id).orElse(null);
    }
    @Override
    public double calculateScore(List<String> values) {
        double note = 0.0;
        for (String value : values) {
            List<Score> scores = scoreVariableRepository.findByValeur(value);
            for (Score score : scores) {
                Variable variable = score.getVariable();
                note += score.getScore() * variable.getCoefficient();
            }
        }
        return note;
    }


}
