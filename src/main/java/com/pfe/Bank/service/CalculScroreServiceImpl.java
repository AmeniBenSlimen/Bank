package com.pfe.Bank.service;

import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public double calculateScore(Map<String, String> values) {
        double note = 0.0;

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

        return note;
    }
}
