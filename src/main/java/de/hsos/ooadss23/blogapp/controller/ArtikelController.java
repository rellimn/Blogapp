package de.hsos.ooadss23.blogapp.controller;

import de.hsos.ooadss23.blogapp.datenbank.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.Math.round;

@Controller
@RequestMapping("/artikel")
@SessionAttributes("nutzerSession")
public class ArtikelController {
    private final BewertungRepository bewertungRepository;
    private final ArtikelRepository artikelRepository;

    ArtikelController(ArtikelRepository artikelRepository, BewertungRepository bewertungRepository) {
        this.artikelRepository = artikelRepository;
        this.bewertungRepository = bewertungRepository;
    }
    @RequestMapping(value="/{artikelId}", method = RequestMethod.GET)
    public String artikelAnzeigen(@PathVariable int artikelId, Model model) {
        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        int nutzerId = nutzerSession.getId();

        model.addAttribute("artikel", this.artikelRepository.findById(artikelId).orElseThrow());
        Optional<Bewertung> nutzerBewertung = this.bewertungRepository.findByArtikelIdAndVerfasserId(artikelId, nutzerId);
        int nutzerSterne = 0;
        if (nutzerBewertung.isPresent())
            nutzerSterne = nutzerBewertung.get().getSterne();
        model.addAttribute("nutzerSterne", nutzerSterne);
        model.addAttribute("sterneSchnitt", round(this.artikelRepository.sterneAvgById(artikelId).orElse(0f)));
        return "artikel/artikel";
    }

    @RequestMapping(value="/{artikelId}/neuerKommentar", method = RequestMethod.GET)
    public String kommentarHinzufuegenForm(@PathVariable int artikelId, Model model) {
        model.addAttribute("artikelId", artikelId);
        return "artikel/neuerKommentar";
    }

    @RequestMapping(value="/{artikelId}/neuerKommentar", method = RequestMethod.POST)
    public String kommentarHinzufuegenHandling(@PathVariable int artikelId, @RequestParam String kommentarText, Model model) {
        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        // NullPointerException kann nicht auftreten, da RequireLoginInterceptor sicherstellt, dass eine nutzerSession vorhanden ist
        Text text = new Text(kommentarText, nutzerSession.getNutzer());
        Kommentar kommentar = new Kommentar(text);
        Artikel artikel = this.artikelRepository.findById(artikelId).orElseThrow();
        artikel.addKommentar(kommentar);
        this.artikelRepository.save(artikel);
        return "redirect:/artikel/" + artikelId;
    }


    @RequestMapping(value="/{artikelId}/neueBewertung", method = RequestMethod.GET)
    public String bewertungHinzufuegenForm(@PathVariable int artikelId, Model model) {
        model.addAttribute("artikelId", artikelId);
        return "artikel/neueBewertung";
    }

    @RequestMapping(value="/{artikelId}/neueBewertung", method = RequestMethod.POST)
    public String bewertungHinzufuegenHandling(@PathVariable int artikelId, @RequestParam int sterne, Model model) {
        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        Artikel artikel = this.artikelRepository.findById(artikelId).orElseThrow();
        // NullPointerException kann nicht auftreten, da RequireLoginInterceptor sicherstellt, dass eine nutzerSession vorhanden ist
        Optional<Bewertung> aktuelleBewertung = this.bewertungRepository.findByArtikelIdAndVerfasserId(artikelId, nutzerSession.getId());
        Bewertung zuSpeichern;
        if (aktuelleBewertung.isPresent()) {
            zuSpeichern = aktuelleBewertung.get();
            zuSpeichern.setSterne(sterne);
        } else {
            zuSpeichern = new Bewertung(sterne, nutzerSession.getNutzer() ,artikel);
        }
        this.bewertungRepository.save(zuSpeichern);
        return "redirect:/artikel/" + artikelId;
    }


}