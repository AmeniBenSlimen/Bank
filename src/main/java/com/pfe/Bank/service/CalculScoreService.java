package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;

import java.util.Map;

public interface CalculScoreService {
    double calculateScore(String values);
    Score addScore(Score score);
    Score getScoreById(long id) throws MissingEntity;

    Score updateScore(Long id, Score updatedScore);
    public Map<String,Boolean> deleteScore(long id) throws MissingEntity;

}
