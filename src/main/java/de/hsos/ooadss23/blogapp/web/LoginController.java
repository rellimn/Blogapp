package de.hsos.ooadss23.blogapp.web;

import de.hsos.ooadss23.blogapp.datenmodell.Nutzer;
import de.hsos.ooadss23.blogapp.repository.NutzerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
@SessionAttributes("nutzerSession")
public class LoginController {
    private final NutzerRepository nutzerRepository;

    public LoginController(NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String haupt() {
        return "redirect:/blogs";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        if (model.getAttribute("nutzerSession") != null)
            return "redirect:/";
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String nutzerAnmelden(@RequestParam String name, Model model) {
        if (model.getAttribute("nutzerSession") != null)
            return "redirect:/";
        Nutzer nutzer;
        Optional<Nutzer> moeglicherNutzer = this.nutzerRepository.findByName(name);
        if (moeglicherNutzer.isPresent())
            nutzer = moeglicherNutzer.get();
        else {
            nutzer = new Nutzer(name);
            this.nutzerRepository.save(nutzer);
        }
        NutzerSession nutzerSession = new NutzerSession(nutzer);
        model.addAttribute(nutzerSession);
        return "redirect:/";
    }

    @RequestMapping(value = "/logout")
    public String nutzerAbmelden(SessionStatus status) {
        status.setComplete();
        return "redirect:login";
    }


}
