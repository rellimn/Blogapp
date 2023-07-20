package de.hsos.ooadss23.blogapp.web;

import de.hsos.ooadss23.blogapp.datenmodell.*;
import de.hsos.ooadss23.blogapp.repository.BlogRepository;
import de.hsos.ooadss23.blogapp.util.NutzerSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Optional;

/**
 * Spring MVC-Controller, der Anfragen unter /blog bedient.
 * @author Roman Wasenmiller
 */
@Controller
@RequestMapping("/blogs")
@SessionAttributes("nutzerSession")
public class BlogController {
    private final BlogRepository blogRepository;

    /**
     * Erstellt einen BlogController.
     * Dependency Injection automatisch mittels Spring.
     * @param blogRepository BlogRepository-Bean
     */
    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * Zeigt alle Blogs an.
     * Zugriff unter /blogs.
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung der Blog-HTML-Seite
     */
    @RequestMapping(method = RequestMethod.GET)
    public String blogsAnzeigen(Model model) {
        model.addAttribute("blogs", this.blogRepository.findAllByOrderByZeitstempelDesc());
        return "blogs/alleBlogs";
    }

    /**
     * Zeigt ein Formular zum Erstellen eines neuen Blogs an.
     * Zugriff unter /blogs/neuerBlog.
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung der "neuer Blog"-HTML-Seite
     */
    @RequestMapping(value="/neuerBlog", method = RequestMethod.GET)
    public String blogHinzufuegenForm(Model model) {
        return "blogs/neuerBlog";
    }

    /**
     * Bearbeitet Post-Request zum Erstellen eines neuen Blogs.
     * Zugriff unter /blogs/neuerBlog.
     * @param titel Titel des zu erstellenden Blogs als POST-Parameter, darf nicht leer sein
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung eines Redirects auf die Blog&uuml;bersicht, oder auf die "neuer Blog"-Seite im Fehlerfall
     */
    @RequestMapping(value="/neuerBlog", method = RequestMethod.POST)
    public String blogHinzufuegenHandling(@RequestParam String titel, Model model) {
        if (titel.isBlank())
            return "redirect:/blogs/neuerBlog";

        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        Blog blog = new Blog(titel, nutzerSession.getNutzer());
        this.blogRepository.save(blog);
        return "redirect:/blogs";
    }

    /**
     * Zeigt alle Artikel eines Blogs an.
     * Zugriff unter /blogs/{blogId}.
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @param blogId ID eines Blogs als URL-Pfadvariable
     * @return String-Darstellung der "alle Artikel eines Blogs"-HTML-Seite, oder Redirect auf Hauptseite im Fehlerfall
     */
    @RequestMapping(value="/{blogId}", method = RequestMethod.GET)
    public String alleArtikelEinesBlogsAnzeigen(@PathVariable int blogId, Model model) {
        Optional<Blog> blog =  this.blogRepository.findById(blogId);
        if (blog.isEmpty())
            return "redirect:/";
        // Artikel absteigend nach Zeitstempel sortieren, sodass neue Artikel zuerst angezeigt werden
        blog.get().getArtikel().sort(Comparator.comparing((Artikel artikel) -> artikel.getText().getZeitstempel()).reversed());
        model.addAttribute("blog", blog.get());
        return "blogs/alleArtikelEinesBlogs.html";
    }

    /**
     * Zeigt ein Formular zum Erstellen eines neuen Artikels an.
     * Zugriff unter /blogs/{blogId}/neuerArtikel.
     * @param blogId ID eines Blogs als URL-Pfadvariable
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung der "neuer Artikel"-HTML-Seite, oder Redirect auf Hautpseite im Fehlerfall
     */
    @RequestMapping(value="/{blogId}/neuerArtikel", method = RequestMethod.GET)
    public String artikelHinzufuegenForm(@PathVariable int blogId, Model model) {
        Optional<Blog> blog =  this.blogRepository.findById(blogId);
        if (blog.isEmpty())
            return "redirect:/";

        model.addAttribute("blogId", blogId);
        return "blogs/neuerArtikel";
    }

    /**
     * Bearbeitet Post-Request zum Erstellen eines neuen Blogartikels
     * Zugriff unter /blogs/{blogId}/neuerArtikel.
     * @param blogId ID eines Blogs als URL-Pfadvariable
     * @param ueberschrift &uuml;berschrift des zu erstellenden Artikels als POST-Parameter, darf nicht leer sein
     * @param artikelText Text des zu erstellenden Artikels als POST-Parameter, darf nicht leer sein
     * @param model Spring MVC-Model, automatisch von Spring übergeben
     * @return String-Darstellung eines Redirect auf die entsprechende Blogseite, oder "neuer Artikel"-Seite im Fehlerfall, oder Hauptseite, falls blogId keinem Blog zuzuordnen ist
     */
    @RequestMapping(value="/{blogId}/neuerArtikel", method = RequestMethod.POST)
    public String artikelHinzufuegenHandling(@PathVariable int blogId, @RequestParam String ueberschrift, @RequestParam String artikelText, Model model) {
        Optional<Blog> blog =  this.blogRepository.findById(blogId);
        if (blog.isEmpty())
            return "redirect:/";
        if (ueberschrift.isBlank() || artikelText.isBlank())
            return String.format("redirect:/blogs/%d/neuerArtikel", blogId);

        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        // Exception werfen, wenn Blogersteller und eingeloggter Nutzer nicht gleich sind
        if (!blog.get().getErsteller().equals(nutzerSession.getNutzer()))
            throw new IllegalStateException("Eingeloggter Nutzer ist nicht Blogersteller");
        Text text = new Text(artikelText, nutzerSession.getNutzer());
        Artikel artikel = new Artikel(ueberschrift, text);
        blog.get().addArtikel(artikel);
        this.blogRepository.save(blog.get());
        return "redirect:/blogs/" + blogId;
    }
}
