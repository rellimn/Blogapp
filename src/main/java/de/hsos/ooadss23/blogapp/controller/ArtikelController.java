package de.hsos.ooadss23.blogapp.controller;

import de.hsos.ooadss23.blogapp.datenbank.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artikel")
@SessionAttributes("nutzerSession")
public class ArtikelController {
    ArtikelRepository artikelRepository;

    ArtikelController(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }
    @RequestMapping(value="/{artikelId}", method = RequestMethod.GET)
    public String artikelAnzeigen(@PathVariable int artikelId, Model model) {
        model.addAttribute("artikel", this.artikelRepository.findById(artikelId).orElseThrow());
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
        // NullPointerException kann nicht auftreten, da RequireLoginInterceptor sicherstellt, dass eine nutzerSession vorhanden ist

        Artikel artikel = this.artikelRepository.findById(artikelId).orElseThrow();
        Bewertung bewertung = new Bewertung(sterne,nutzerSession.getNutzer(),artikel);
        artikel.addBewertungen(bewertung);
        this.artikelRepository.save(artikel);


        //hier fehlt was
        return "redirect:/artikel/" + artikelId;
    }


}