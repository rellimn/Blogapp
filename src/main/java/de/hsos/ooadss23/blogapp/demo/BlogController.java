package de.hsos.ooadss23.blogapp.demo;

import de.hsos.ooadss23.blogapp.entitaet.Artikel;
import de.hsos.ooadss23.blogapp.entitaet.Blog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @RequestMapping(method = RequestMethod.GET)
    public String blogsAnzeigen(Model model) {
        List<Blog> blogs;
        try(Datenbankanbindung db = new Datenbankanbindung()) {
            blogs = db.alleBlogs();
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
        model.addAttribute("blogs", blogs);
        return "blogs/alleBlogs";
    }

    @RequestMapping(value="/{blogId}", method = RequestMethod.GET)
    public String alleArtikelEinesBlogsAnzeigen(@PathVariable int blogId, Model model) {
        List<Artikel> artikelListe;
        try(Datenbankanbindung db = new Datenbankanbindung()) {
            artikelListe = db.alleArtikelInBlog(blogId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
        model.addAttribute("artikelListe", artikelListe);
        return "blogs/alleArtikelEinesBlogs.html";
    }
}
