package com.pfe.Bank.repository;

import com.pfe.Bank.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VariableRepository extends JpaRepository<Variable,Long> {
    Optional<Variable> findByCode (String code);
    Optional<Variable> findById(Long id);
    void deleteById(Long id);
    @Query("SELECT v FROM Variable v LEFT JOIN FETCH v.scores WHERE v.id = :id")
    Optional<Variable> findByIdWithScores(@Param("id") Long id);

}
