package com.pfe.Bank.repository;

import com.pfe.Bank.model.Modul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Modul, String> {
    //List<Modul> findByIdIn(List<Modul> moduleIds);

    //List<Modul> getModules(List<Modul> modules);
    Optional<Modul> findByCodmodule(String codmodule);


}
