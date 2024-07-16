package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VariableService {
    Modele findById(Long id) throws MissingEntity;

    //Variable addVariable(String code, String description, Double coefficient, Type type, long modeleId) throws MissingEntity;

    Variable addVariable(Variable variable);
    List<Variable> getAllVariable();
    Variable getVariableById(long id) throws MissingEntity;
    Optional<Variable> getVariableWithScores(Long id);
    Variable updateVariable(Long id, Variable updatedVariable);
    Variable save(Variable variable);
    public Variable findById(long id);
    double calculateScore(List<String> values);

    }
