package com.pfe.Bank.repository;

import com.pfe.Bank.model.Client;
import com.pfe.Bank.model.ClientRetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRetailRepository extends JpaRepository<ClientRetail,Long> {
    //ClientRetail findByCodeRelation(Long codeRelation);
    Optional<ClientRetail> findByCodeRelation(Long codeRelation);


}
