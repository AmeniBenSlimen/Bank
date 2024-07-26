package com.pfe.Bank.repository;

import com.pfe.Bank.dto.ModeleDto;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.SituationClientRetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModeleRepository extends JpaRepository<Modele,Long> {
    List<Modele> findByDisabledFalse();
    @Query("SELECT m FROM Modele m WHERE m.disabled = true")
    List<Modele> findModelesToBeSoftDisabled();
    @Query("SELECT m FROM Modele m WHERE m.disabled = false")
    List<Modele> findModelesSoftDisabled();
    List<Modele> findByUsed(boolean used);
    List<Modele> findByNameAndAnnee(String name, int annee);
    Optional<Modele> findByUsedTrue();


}





