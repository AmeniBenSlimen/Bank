package com.pfe.Bank.repository;

import com.pfe.Bank.model.Notation;
import com.pfe.Bank.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    long deleteByNotation(Notation notation);
    List<Response> findByNotationId(Long notationId);
}
