package de.hsos.ooadss23.blogapp.demo;

import de.hsos.ooadss23.blogapp.entitaet.Artikel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/artikel")
public class ArtikelController {
    @RequestMapping(value="/{artikelId}", method = RequestMethod.GET)
    public String einenArtikelAnzeigen(@PathVariable int artikelId, Model model) {
        Artikel artikel;
        try(Datenbankanbindung db = new Datenbankanbindung()) {
            artikel = db.getArtikel(artikelId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
        model.addAttribute("artikel", artikel);
        return "artikel/artikel";
    }
}