package com.pfe.Bank.repository;

import com.pfe.Bank.model.Client;
import com.pfe.Bank.model.SituationClientRetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByCodeRelationAndIdNat(long codeRelation, String idNat);
    Client findByCodeRelation(Long codeRelation);
    List<Client> findByCodeRelation(long codeRelation);


}
