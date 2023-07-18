package de.hsos.ooadss23.blogapp.controller;

import de.hsos.ooadss23.blogapp.datenbank.Nutzer;
import de.hsos.ooadss23.blogapp.datenbank.NutzerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
@SessionAttributes("nutzerSession")
public class LoginController {
    NutzerRepository nutzerRepository;

    public LoginController(NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String haupt() {
        return "redirect:login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String nutzerAnmelden(@RequestParam String name, Model model) {
        Nutzer nutzer;
        Optional<Nutzer> opt;
        if ((opt = this.nutzerRepository.findByName(name)).isPresent())
            nutzer = opt.get();
        else {
            nutzer = new Nutzer(name);
            this.nutzerRepository.save(nutzer);
        }
        NutzerSession nutzerSession = new NutzerSession(nutzer);
        model.addAttribute(nutzerSession);
        return "redirect:blogs";
    }

    @RequestMapping(value = "/logout")
    public String nutzerAbmelden(SessionStatus status) {
        status.setComplete();
        return "redirect:login";
    }


}
