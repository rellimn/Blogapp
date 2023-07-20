package de.hsos.ooadss23.blogapp.repository;

import de.hsos.ooadss23.blogapp.datenmodell.Artikel;
import de.hsos.ooadss23.blogapp.datenmodell.Bewertung;
import de.hsos.ooadss23.blogapp.datenmodell.Nutzer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Spring Data-Repository, das Bewertungen verwaltet
 * @author Roman Wasenmiller
 */
public interface BewertungRepository extends CrudRepository<Bewertung, Integer> {
    /**
     * Findet Bewertung mittels einer Artikel-ID und einer Nutzer-ID
     * @param artikelId ID eines Artikels
     * @param nutzerId ID eines Nutzers
     * @return Entsprechende Bewertung in Optional gekapselt
     */
    Optional<Bewertung> findByArtikelIdAndVerfasserId(int artikelId, int nutzerId);
}
