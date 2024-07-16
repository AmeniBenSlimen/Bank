package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
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
    public Score addScore(Score score) {
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
        existingScore.setValeur(updatedScore.getValeur());
        existingScore.setScore(updatedScore.getScore());

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
}
