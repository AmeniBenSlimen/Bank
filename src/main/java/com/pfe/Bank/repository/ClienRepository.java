package com.pfe.Bank.repository;

import com.pfe.Bank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienRepository extends JpaRepository<Client,Long> {
}
