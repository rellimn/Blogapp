package de.hsos.ooadss23.blogapp.datenbank;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Nutzer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "ersteller", fetch = FetchType.LAZY)
    private List<Blog> blogs;
    @OneToMany(orphanRemoval = true, cascade =  CascadeType.ALL, mappedBy = "verfasser", fetch = FetchType.LAZY)
    private List<Text> texte;

    protected Nutzer() {

    }

    public Nutzer(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public void addBlog(Blog blog) {
        this.blogs.add(blog);
        blog.setErsteller(this);
    }

    public List<Text> getTexte() {
        return texte;
    }

    public void setTexte(List<Text> texte) {
        this.texte = texte;
    }

    public void removeBlog(Blog blog) {
        this.blogs.remove(blog);
        blog.setErsteller(null);
    }

    public void addText(Text text) {
        this.texte.add(text);
        text.setVerfasser(this);
    }

    public void removeText(Text text) {
        this.texte.remove(text);
        text.setVerfasser(null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nutzer nutzer)) return false;
        return id == nutzer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
