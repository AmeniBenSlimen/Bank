package com.pfe.Bank.service;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VariableService {
    //Modele findById(Long id) throws MissingEntity;

    List<Variable> getAllVariable();

    Variable getVariableById(long id) throws MissingEntity;


    Variable updateVariable(Long id, Variable updatedVariable);

    Variable save(Variable variable);

    public Variable findById(long id) throws MissingEntity;

    // double calculateScore(List<String> values);
    public void addScoreToVariable(long variableId, Score score);
    public Variable createVariable(Variable variable, long modelId);
    Variable findByIdVariable(Long id);
    public Optional<Variable> getVariableWithScores(Long id);
    public List<ScoreDto> getScoresByVariableId(Long variableId);
    void deleteVariable(Long id);
    double calculatePonderationForVariable(Long variableId);
    List<VariableDto> getVariablesByModeleId(Long modeleId);
    public List<Variable> getVariablesByActiveModele();
}
