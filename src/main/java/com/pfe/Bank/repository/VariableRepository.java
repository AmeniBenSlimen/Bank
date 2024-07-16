package com.pfe.Bank.repository;

import com.pfe.Bank.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VariableRepository extends JpaRepository<Variable,Long> {
    Optional<Variable> findByCode (String code);
    Optional<Variable> findById(Long id);
    void deleteById(Long id);


}
