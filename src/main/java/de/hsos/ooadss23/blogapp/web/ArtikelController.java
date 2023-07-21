package de.hsos.ooadss23.blogapp.web;

import de.hsos.ooadss23.blogapp.datenmodell.*;
import de.hsos.ooadss23.blogapp.repository.ArtikelRepository;
import de.hsos.ooadss23.blogapp.repository.BewertungRepository;
import de.hsos.ooadss23.blogapp.util.NutzerSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.Math.round;

/**
 * Spring MVC-Controller, der Anfragen unter /artikel bedient.
 * @author Roman Wasenmiller
 */
@Controller
@RequestMapping("/artikel")
@SessionAttributes("nutzerSession")
public class ArtikelController {
    private final BewertungRepository bewertungRepository;
    private final ArtikelRepository artikelRepository;

    /**
     * Erstellt einen ArtikelController.
     * Dependency Injection automatisch mittels Spring.
     * @param artikelRepository ArtikelRepository-Bean
     * @param bewertungRepository BewertungRepository-Bean
     */
    ArtikelController(ArtikelRepository artikelRepository, BewertungRepository bewertungRepository) {
        this.artikelRepository = artikelRepository;
        this.bewertungRepository = bewertungRepository;
    }

    /**
     * Zeigt alle Artikel eines Blogs an.
     * Zugriff unter /artikel/{artikelId}.
     * @param artikelId ID eines Artikels als URL-Pfadvariable
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung der Artikel-HTML-Seite, oder Redirect im Fehlerfall
     */
    @RequestMapping(value="/{artikelId}", method = RequestMethod.GET)
    public String artikelAnzeigen(@PathVariable int artikelId, Model model) {
        Optional<Artikel> artikel = this.artikelRepository.findById(artikelId);
        if (artikel.isEmpty()) {
            return "redirect:/";
        }

        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        int nutzerId = nutzerSession.getId();

        model.addAttribute("artikel", artikel.get());
        Optional<Bewertung> nutzerBewertung = this.bewertungRepository.findByArtikelIdAndVerfasserId(artikelId, nutzerId);
        // Keine abgegebene Bewertung entspricht null Sternen
        int nutzerSterne = 0;
        if (nutzerBewertung.isPresent()) {
            nutzerSterne = nutzerBewertung.get().getSterne();
        }
        model.addAttribute("nutzerSterne", nutzerSterne);
        model.addAttribute("sterneSchnitt", this.artikelRepository.sterneAvgById(artikelId).orElse(0f));
        return "artikel/artikel";
    }

    /**
     * Zeigt ein Formular zum Erstellen eines neuen Artikel-Kommentars an.
     * Zugriff unter /artikel/{artikelId}/neuerKommentar.
     * @param artikelId ID eines Artikels als URL-Pfadvariable
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung der "neuer Kommentar"-HTML-Seite
     */
    @RequestMapping(value="/{artikelId}/neuerKommentar", method = RequestMethod.GET)
    public String kommentarHinzufuegenForm(@PathVariable int artikelId, Model model) {
        model.addAttribute("artikelId", artikelId);
        return "artikel/neuerKommentar";
    }

    /**
     * Bearbeitet Post-Request zum Erstellen eines neuen Artikel-Kommentars.
     * Zugriff unter /artikel/{artikelId}/neuerKommentar.
     * @param artikelId ID eines Artikels als URL-Pfadvariable
     * @param kommentarText Text des zu erstellenden Kommentars als POST-Parameter, darf nicht leer sein
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung eines Redirect auf die entsprechende Artikelseite, oder "neuer Kommentar"-Seite im Fehlerfall
     */
    @RequestMapping(value="/{artikelId}/neuerKommentar", method = RequestMethod.POST)
    public String kommentarHinzufuegenHandling(@PathVariable int artikelId, @RequestParam String kommentarText, Model model) {
        Optional<Artikel> artikel = this.artikelRepository.findById(artikelId);
        if (artikel.isEmpty() || kommentarText.isBlank()) {
            return String.format("redirect:/artikel/%d/neuerKommentar", artikelId);
        }

        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        Text text = new Text(kommentarText, nutzerSession.getNutzer());
        Kommentar kommentar = new Kommentar(text);

        artikel.get().addKommentar(kommentar);
        this.artikelRepository.save(artikel.get());
        return "redirect:/artikel/" + artikelId;
    }

    /**
     * Bearbeitet Post-Request zum Hinzufügen einer neuen, oder Bearbeiten einer bestehenden Artikelbewertung.
     * Zugriff unter /artikel/{artikelId}/neueBewertung.
     * @param artikelId ID eines Artikels als URL-Pfadvariable
     * @param sterne Anzahl an Sternen zwischen einschließlich 1 und 5
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung eines Redirects auf die entsprechente Artikel-Seite
     */
    @RequestMapping(value="/{artikelId}/neueBewertung", method = RequestMethod.POST)
    public String bewertungHinzufuegenHandling(@PathVariable int artikelId, @RequestParam int sterne, Model model) {
        Optional<Artikel> artikel = this.artikelRepository.findById(artikelId);
        if (sterne < 1 || sterne > 5 || artikel.isEmpty()) {
            return "redirect:/artikel/" + artikelId;
        }

        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");

        Optional<Bewertung> aktuelleBewertung = this.bewertungRepository.findByArtikelIdAndVerfasserId(artikelId, nutzerSession.getId());
        // Wenn Nutzer schon eine Bewertung abgegeben hat, wird die bestehende Bewertung geändert, sonst wird eine neue angelegt
        Bewertung zuSpeichern;
        if (aktuelleBewertung.isPresent()) {
            zuSpeichern = aktuelleBewertung.get();
            zuSpeichern.setSterne(sterne);
        } else {
            zuSpeichern = new Bewertung(sterne, nutzerSession.getNutzer(), artikel.get());
        }
        this.bewertungRepository.save(zuSpeichern);
        return "redirect:/artikel/" + artikelId;
    }


}