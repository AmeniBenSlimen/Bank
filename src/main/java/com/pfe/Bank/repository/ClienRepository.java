package com.pfe.Bank.repository;

import com.pfe.Bank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienRepository extends JpaRepository<Client,Long> {
    //boolean existsByCodeRelationAndIdNat(long codeRelation, String idNat);
    Optional<Client> findByCodeRelationAndIdNat(long codeRelation, String idNat);

}
