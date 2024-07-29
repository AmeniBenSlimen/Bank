package com.pfe.Bank.service;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ModeleRepository;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /*@Override
    public Modele findById(Long id) throws MissingEntity {
        Optional<Modele> modele = modeleRepository.findById(id);
        if(!modele.isPresent()){
            throw new MissingEntity("Modele not found with Id Modele : "+id);
        }
        return modele.get();
    }*/
    /*@Override
    public Variable addVariable(Variable variable) {

            return variableRepository.save(variable);
    }*/
    public void addScoreToVariable(long variableId, Score score) throws IllegalArgumentException {
        Variable variable = variableRepository.findById(variableId)
                .orElseThrow(() -> new IllegalArgumentException("Variable not found"));

        if ((variable.getType() == Type.NUMBER && !(score instanceof NUMBER)) ||
                (variable.getType() == Type.DATE && !(score instanceof DATE)) ||
                (variable.getType() == Type.ENUMERATION && !(score instanceof ENUMERATION)) ||
                (variable.getType() == Type.INTERVALE && !(score instanceof INTERVALE))) {
            throw new IllegalArgumentException("Score type does not match variable type");
        }

        score.setVariable(variable);
        scoreVariableRepository.save(score);
    }
    @Override
    public Variable findByIdVariable(Long id) {
        return variableRepository.findById(id).orElse(null);
    }
    @Override
    public Optional<Variable> getVariableWithScores(Long id) {
        return variableRepository.findByIdWithScores(id);
    }
    @Override
    public Variable createVariable(Variable variable, long modelId) {
        Modele modele = modeleRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Modele not found"));

    variable.setModele(modele);

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
    /*public Optional<Variable> getVariableWithScores(Long id) {
        return variableRepository.findById(id);
    }*/

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
  /*  @Override
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
*/
    @Override
    public List<ScoreDto> getScoresByVariableId(Long variableId) {

        List<Score> scores = scoreVariableRepository.findByVariable_Id(variableId);
        List<ScoreDto> scoreDtos = new ArrayList<>();

        for (Score score : scores) {
            ScoreDto scoreDto = new ScoreDto();
            scoreDto.setId(score.getId());
            scoreDto.setScore(score.getScore());

            if (score instanceof DATE) {
                scoreDto.setValeur(((DATE) score).getValeur().toString());
                //scoreDto.setValue("date");

            } else if (score instanceof ENUMERATION) {
                scoreDto.setValeur(((ENUMERATION) score).getValeur());
                //scoreDto.setValue("enumeration");

            } else if (score instanceof INTERVALE) {
                scoreDto.setVmin(((INTERVALE) score).getvMin());
                scoreDto.setVmax(((INTERVALE) score).getvMax());
               // scoreDto.setValue("intervale");

            } else if (score instanceof NUMBER) {
                scoreDto.setValeur(((NUMBER) score).getValeur().toString());
                //scoreDto.setValue("number");

            }

            scoreDtos.add(scoreDto);
        }

        return scoreDtos;
    }



}
