package com.pfe.Bank.repository;

import com.pfe.Bank.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,String> {
    //List<Menu> findByModule(Modul module);
}
