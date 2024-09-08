package com.pfe.Bank.repository;

import com.pfe.Bank.model.Notation;
import com.pfe.Bank.model.ResponseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotationRepository extends JpaRepository<Notation,Long> {
    List<Notation> findByStatus(ResponseStatus responseStatus);

}
