package com.pfe.Bank.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    //on doit cree une requete qui permet de récupérer tous les token valid (false=valid)
    @Query("select t from Token t inner join User u on t.user.id = u.id where u.id = :userId and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUser(Long userId);
    // on doit afficher le token de l'utilisateur
    Optional<Token> findByToken(String Token);
}
