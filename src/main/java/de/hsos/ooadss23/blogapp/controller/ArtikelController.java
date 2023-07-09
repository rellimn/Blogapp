package de.hsos.ooadss23.blogapp.controller;

import de.hsos.ooadss23.blogapp.datenbank.Artikel;
import de.hsos.ooadss23.blogapp.datenbank.ArtikelRepository;
import de.hsos.ooadss23.blogapp.datenbank.Kommentar;
import de.hsos.ooadss23.blogapp.datenbank.Text;
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
}