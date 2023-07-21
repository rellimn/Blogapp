package de.hsos.ooadss23.blogapp.web;

import de.hsos.ooadss23.blogapp.datenmodell.Nutzer;
import de.hsos.ooadss23.blogapp.repository.NutzerRepository;
import de.hsos.ooadss23.blogapp.util.NutzerSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

/**
 * Spring MVC-Controller für Anfragen, die die Anmeldung und Hauptseite (unter /) betreffen.
 * @author Roman Wasenmiller
 */
@Controller
@SessionAttributes("nutzerSession")
public class LoginController {
    private final NutzerRepository nutzerRepository;

    /**
     * Erstellt einen LoginController.
     * Dependency Injection automatisch mittels Spring.
     * @param nutzerRepository NutzerRepository-Bean
     */
    public LoginController(NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
    }

    /**
     * Zeigt die Hauptseite an.
     * Zugriff unter /.
     * Leitet weiter auf /blogs.
     * @return String-Darstellung des Redirects auf die Blog&uuml;bersicht
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String haupt() {
        return "redirect:/blogs";
    }

    /**
     * Zeigt ein Anmeldeformular an.
     * Zugriff unter /login.
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung der Loginseite oder Redirect auf Hauptseite, falls Nutzer schon eingeloggt
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        // Wenn schon eingeloggt, weiterleiten auf Hauptseite
        if (model.getAttribute("nutzerSession") != null)
            return "redirect:/";
        return "login";
    }

    /**
     * Bearbeitet POST-Request zum Anmelden eines Nutzers.
     * Zugriff unter /login.
     * @param name Name des Nutzers
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung des Redirects auf die Hauptseite, oder Login-Seite im Fehlerfall
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String nutzerAnmelden(@RequestParam String name, Model model) {
        if (model.getAttribute("nutzerSession") != null) {
            return "redirect:/";
        }
        if (name.isBlank()) {
            return "redirect:/login";
        }
        Nutzer nutzer;
        Optional<Nutzer> moeglicherNutzer = this.nutzerRepository.findByName(name);
        if (moeglicherNutzer.isPresent()) {
            nutzer = moeglicherNutzer.get();
        } else {
            nutzer = new Nutzer(name);
            this.nutzerRepository.save(nutzer);
        }
        NutzerSession nutzerSession = new NutzerSession(nutzer);
        model.addAttribute(nutzerSession);
        return "redirect:/";
    }

    /**
     * Meldet Nutzer ab und leitet auf Login-Seite weiter.
     * Zugriff unter /logout.
     * @param status Spring SessionStatus, automatisch von Spring übergeben
     * @return String-Darstellung des Redirects auf die Login-Setie
     */
    @RequestMapping(value = "/logout")
    public String nutzerAbmelden(SessionStatus status) {
        // Session beenden
        status.setComplete();
        return "redirect:login";
    }


}
