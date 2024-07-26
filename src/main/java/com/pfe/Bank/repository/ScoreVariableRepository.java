package com.pfe.Bank.repository;

import com.pfe.Bank.model.Score;
import com.pfe.Bank.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreVariableRepository extends JpaRepository<Score,Long> {
    List<Score> findByVariable(Variable variable);
    @Query("SELECT s FROM Score s WHERE s.variable.id = :variableId")
    List<Score> findByVariableId(@Param("variableId") Long variableId);

}
