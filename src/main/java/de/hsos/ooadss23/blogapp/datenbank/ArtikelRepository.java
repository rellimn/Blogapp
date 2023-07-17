package de.hsos.ooadss23.blogapp.datenbank;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArtikelRepository extends CrudRepository<Artikel, Integer> {

    @Query("""
        SELECT AVG(b.sterne)
          FROM Artikel a
          JOIN a.bewertungen b
         WHERE a.id = ?1
    """)
    Optional<Float> sterneAvgById(int id);
}
