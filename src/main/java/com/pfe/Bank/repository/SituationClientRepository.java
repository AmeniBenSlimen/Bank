package com.pfe.Bank.repository;

import com.pfe.Bank.model.ClientRetail;
import com.pfe.Bank.model.SituationClientRetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SituationClientRepository extends JpaRepository<SituationClientRetail,Long> {
    Optional<SituationClientRetail> findByClientAndDateDeSituation(ClientRetail client, Date dateDeSituation);
    List<SituationClientRetail> findByClientId(long clientId);
    List<SituationClientRetail> findByCodeRelation(long codeRelation);}
