package de.hsos.ooadss23.blogapp.repository;

import de.hsos.ooadss23.blogapp.datenmodell.Nutzer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Spring Data-Repository, das Nutzer verwaltet
 * @author Roman Wasenmiller
 */
public interface NutzerRepository extends CrudRepository<Nutzer, Integer> {
    /**
     * Findet Nutzer mittels Namen
     * @param name Name des Nutzers
     * @return Nutzer in Optional gekapselt
     */
    Optional<Nutzer> findByName(String name);
}
