package de.hsos.ooadss23.blogapp.datenmodell;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA-Entit&auml;t, die einen Blogartikel repr&auml;sentiert.
 * Entspricht der Datenbanktabelle "Artikel".
 * @author Roman Wasenmiller
 */
@Entity
public class Artikel {
    //test
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
    @OneToMany(orphanRemoval = true, cascade =  CascadeType.ALL, mappedBy = "artikel", fetch = FetchType.LAZY)
    private List<Bewertung> bewertungen;

    protected Artikel() {

    }

    public Artikel(String ueberschrift, Text text) {   //hier noch bewertungen einfuegen
        this.ueberschrift = ueberschrift;
        this.text = text;
        this.kommentare = new ArrayList<>();
    }

    public List<Bewertung> getBewertungen() {
        return bewertungen;
    }

    public void setBewertungen(List<Bewertung> bewertungen) {
        this.bewertungen = bewertungen;
    }

    public void addBewertung(Bewertung bewertung) {
        this.bewertungen.add(bewertung);
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

    @Override
    public String toString() {
        return "Artikel{" +
                "id=" + id +
                ", ueberschrift='" + ueberschrift + '\'' +
                ", text=" + text +
                ", kommentare=" + kommentare +
                ", blog=" + blog +
                ", bewertungen=" + bewertungen +
                '}';
    }
}
