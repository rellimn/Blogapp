package de.hsos.ooadss23.blogapp.datenbank;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NutzerRepository extends CrudRepository<Nutzer, Integer> {
    Optional<Nutzer> findByName(String name);
}
