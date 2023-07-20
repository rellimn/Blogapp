package de.hsos.ooadss23.blogapp.web;

import de.hsos.ooadss23.blogapp.datenmodell.*;
import de.hsos.ooadss23.blogapp.repository.BlogRepository;
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

    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String blogsAnzeigen(Model model) {
        model.addAttribute("blogs", this.blogRepository.findAllByOrderByZeitstempelDesc());
        return "blogs/alleBlogs";
    }

    @RequestMapping(value="/neuerBlog", method = RequestMethod.GET)
    public String blogHinzufuegenForm(Model model) {
        return "blogs/neuerBlog";
    }

    @RequestMapping(value="/neuerBlog", method = RequestMethod.POST)
    public String blogHinzufuegenHandling(@RequestParam String titel, Model model) {
        if (titel.strip().equals(""))
            throw new IllegalArgumentException("Titel darf nicht leer sein");

        NutzerSession nutzerSession = (NutzerSession) model.getAttribute("nutzerSession");
        Blog blog = new Blog(titel, nutzerSession.getNutzer());
        this.blogRepository.save(blog);
        return "redirect:/blogs";
    }

    @RequestMapping(value="/{blogId}", method = RequestMethod.GET)
    public String alleArtikelEinesBlogsAnzeigen(@PathVariable int blogId, Model model) {
        Optional<Blog> blog =  this.blogRepository.findById(blogId);
        if (blog.isEmpty())
            return "redirect:/";
        // Artikel absteigend nach Zeitstempel sortieren, sodass neue Artikel zuerst angezeigt werden
        blog.get().getArtikel().sort(Comparator.comparing((Artikel artikel) -> artikel.getText().getZeitstempel()).reversed());
        model.addAttribute("blog", blog);
        return "blogs/alleArtikelEinesBlogs.html";
    }

    @RequestMapping(value="/{blogId}/neuerArtikel", method = RequestMethod.GET)
    public String kommentarHinzufuegenForm(@PathVariable int blogId, Model model) {
        Optional<Blog> blog =  this.blogRepository.findById(blogId);
        if (blog.isEmpty())
            return "redirect:/";

        model.addAttribute("blogId", blogId);
        return "blogs/neuerArtikel";
    }

    @RequestMapping(value="/{blogId}/neuerArtikel", method = RequestMethod.POST)
    public String artikelHinzufuegenHandling(@PathVariable int blogId, @RequestParam String ueberschrift, @RequestParam String artikelText, Model model) {
        Optional<Blog> blog =  this.blogRepository.findById(blogId);
        if (blog.isEmpty())
            return "redirect:/";
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
