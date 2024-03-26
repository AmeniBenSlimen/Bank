package com.pfe.Bank.repository;

import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,String> {
    //List<Menu> findByModule(Modul module);
    Optional<Menu> findByCodmenu(String codmenu);

}
