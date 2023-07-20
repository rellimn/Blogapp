package de.hsos.ooadss23.blogapp.repository;

import de.hsos.ooadss23.blogapp.datenmodell.Artikel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Spring Data-Repository, das Artikel verwaltet
 * @author Roman Wasenmiller
 */
public interface ArtikelRepository extends CrudRepository<Artikel, Integer> {

    /**
     * Berechnet den Durchschnitt aller Sternebewertungen eines Artikels
     * @param id ID des Artikels
     * @return Durchschnitt aller Sternebewertungen eines Artikels
     */
    @Query("""
        SELECT AVG(b.sterne)
          FROM Artikel a
          JOIN a.bewertungen b
         WHERE a.id = ?1
    """)
    Optional<Float> sterneAvgById(int id);
}
