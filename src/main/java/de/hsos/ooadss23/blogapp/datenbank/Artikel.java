package de.hsos.ooadss23.blogapp.datenbank;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Artikel {
    @Id
    @GeneratedValue
    private int id;
    private String ueberschrift;
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Text text;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "artikel")
    private List<Kommentar> kommentare;
    @ManyToOne(fetch = FetchType.LAZY)
    private Blog blog;

    protected Artikel() {

    }

    public Artikel(String ueberschrift, Text text) {
        this.ueberschrift = ueberschrift;
        this.text = text;
        this.kommentare = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public List<Kommentar> getKommentare() {
        return kommentare;
    }

    public void setKommentare(List<Kommentar> kommentare) {
        this.kommentare = kommentare;
    }

    public void addKommentar(Kommentar kommentar) {
        this.kommentare.add(kommentar);
        kommentar.setArtikel(this);
    }

    public void removeKommentar(Kommentar kommentar) {
        this.kommentare.remove(kommentar);
        kommentar.setArtikel(null);
    }
    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikel artikel)) return false;
        return id == artikel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
