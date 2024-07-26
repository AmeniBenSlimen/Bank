package com.pfe.Bank.service;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;

import java.util.List;
import java.util.Map;

public interface CalculScoreService {
    double calculateScore(String values);
    Score addScore(ScoreDto scoreDto);
    Score getScoreById(long id) throws MissingEntity;

    Score updateScore(Long id, Score updatedScore);
    public Map<String,Boolean> deleteScore(long id) throws MissingEntity;
    public Score saveScore(Score score);
    public List<SVDate> getAllSVDates();

    public List<SVEnum> getAllVEnums();

    public List<SVInterval> getAllSVIntervals();

    public List<SVNumber> getAllVNumbers();

}
