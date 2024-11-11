package com.pfe.Bank.repository;

import com.pfe.Bank.model.Appreciation;
import com.pfe.Bank.model.Notation;
import com.pfe.Bank.model.ResponseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotationRepository extends JpaRepository<Notation,Long> {
    List<Notation> findByStatus(ResponseStatus responseStatus);
    List<Notation> findByClientId(Long clientId);
    long countByAppreciation(Appreciation appreciation);
    Notation findFirstByClientId(Long clientId);
    List<Notation> findByClientIdAndStatus(long clientId, ResponseStatus status);
}
