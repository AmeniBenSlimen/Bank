package com.pfe.Bank.repository;

import com.pfe.Bank.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModeleRepository extends JpaRepository<Modele,Long> {
    List<Modele> findByDeletedFalse();
    @Query("SELECT m FROM Modele m WHERE m.deleted = true")
    List<Modele> findModelesToBeSoftDeleted();
    @Query("SELECT m FROM Modele m WHERE m.deleted = false")
    List<Modele> findModelesSoftDeleted();
    List<Modele> findByUsed(boolean used);

}
