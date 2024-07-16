package com.pfe.Bank.repository;

import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreVariableRepository extends JpaRepository<Score,Long> {
    List<Score> findByVariable(Variable variable);
    Optional<Score> findById(Long id);
    List<Score> findByValeur(String valeur);


}
