package de.hsos.ooadss23.blogapp.datenbank;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BewertungRepository extends CrudRepository<Bewertung, Integer> {
    Optional<Bewertung> findByArtikelAndVerfasser(Artikel artikel, Nutzer nutzer);
    Optional<Bewertung> findByArtikelIdAndVerfasserId(int artikelId, int nutzerId);
}
