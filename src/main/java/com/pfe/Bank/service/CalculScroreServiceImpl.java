package com.pfe.Bank.service;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CalculScroreServiceImpl implements CalculScoreService{
    @Autowired
    ScoreVariableRepository scoreVariableRepository;
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    private SVDateRepository svDateRepository;
    @Autowired
    private VEnumRepository svEnumRepository;
    @Autowired
    private SVIntervalRepository svIntervalRepository;
    @Autowired
    private SVNumberRepository svNumberRepository;
    @Override
    public double calculateScore(String values) {
        return 0;
        /*double note = 0.0;

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String code = entry.getKey();
            String value = entry.getValue();

            Optional<Variable> optionalVariable = variableRepository.findByCode(code);
            if (optionalVariable.isPresent()) {
                Variable variable = optionalVariable.get();
                List<Score> scoreList = scoreVariableRepository.findByVariable(variable);

                for (Score score : scoreList) {
                    if (score.getValeur().equals(value)) {
                        note += score.getScore() * variable.getCoefficient();
                    }
                }
            }
        }

        return note;*/
    }

    @Override
    public Score addScore(ScoreDto scoreDto) {
        return null;
    }

    public Score saveScore(Score score) {
        return scoreVariableRepository.save(score);
    }

    @Override
    public Score getScoreById(long id) throws MissingEntity {
        Optional<Score> optional = scoreVariableRepository.findById(id);
        if(!optional.isPresent()){
            throw new MissingEntity("Score not found with ID : "+id);
        }
        return optional.get();
    }

    @Override
    public Score updateScore(Long id, Score updatedScore) {
        Score existingScore = scoreVariableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Score not found with id: " + id));

        existingScore.setScore(updatedScore.getScore());

        if (existingScore instanceof NUMBER && updatedScore instanceof NUMBER) {
            ((NUMBER) existingScore).setValeur(((NUMBER) updatedScore).getValeur());
        } else if (existingScore instanceof ENUMERATION && updatedScore instanceof ENUMERATION) {
            ((ENUMERATION) existingScore).setValeur(((ENUMERATION) updatedScore).getValeur());
        } else if (existingScore instanceof INTERVALE && updatedScore instanceof INTERVALE) {
            ((INTERVALE) existingScore).setvMin(((INTERVALE) updatedScore).getvMin());
            ((INTERVALE) existingScore).setvMax(((INTERVALE) updatedScore).getvMax());
        } else if (existingScore instanceof DATE && updatedScore instanceof DATE) {
            ((DATE) existingScore).setValeur(((DATE) updatedScore).getValeur());
        } else {
            throw new IllegalArgumentException("Unsupported score type");
        }

        return scoreVariableRepository.save(existingScore);
    }


    @Override
    public Map<String, Boolean> deleteScore(long id) throws MissingEntity {
        Score score = getScoreById(id);
        scoreVariableRepository.delete(score);
        Map<String,Boolean> map = new HashMap<>();
        map.put("deleted",Boolean.TRUE);
        return map;
    }
    @Override
    public List<DATE> getAllSVDates() {
        return svDateRepository.findAll();
    }
    @Override
    public List<ENUMERATION> getAllVEnums() {
        return svEnumRepository.findAll();
    }
    @Override
    public List<INTERVALE> getAllSVIntervals() {
        return svIntervalRepository.findAll();
    }
    @Override
    public List<NUMBER> getAllVNumbers() {
        return svNumberRepository.findAll();
    }
    @Override
    public Optional<Score> findById(Long id) {
        return scoreVariableRepository.findById(id);
    }
}
